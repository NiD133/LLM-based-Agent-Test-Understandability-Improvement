package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testParseWithSkip implements UnixStat {

    /**
     * Header-ID of a ZipExtraField not supported by Commons Compress.
     *
     * <p>
     * Used to be ZipShort(1) but this is the ID of the Zip64 extra field.
     * </p>
     */
    static final ZipShort UNRECOGNIZED_HEADER = new ZipShort(0x5555);

    /**
     * Header-ID of a ZipExtraField not supported by Commons Compress used for the ArrayIndexOutOfBoundsTest.
     */
    static final ZipShort AIOB_HEADER = new ZipShort(0x1000);

    private static final int EXTRA_FIELD_HEADER_LENGTH = 4;
    private static final int EXPECTED_ASI_DIRECTORY_MODE = 040755;

    private AsiExtraField a;

    private UnrecognizedExtraField dummy;

    private byte[] data;

    private byte[] aLocal;

    @BeforeEach
    public void setUp() {
        a = new AsiExtraField();
        a.setMode(0755);
        a.setDirectory(true);

        dummy = new UnrecognizedExtraField();
        dummy.setHeaderId(UNRECOGNIZED_HEADER);
        dummy.setLocalFileDataData(new byte[] { 0 });
        dummy.setCentralDirectoryData(new byte[] { 0 });

        aLocal = a.getLocalFileDataData();
        final byte[] dummyLocal = dummy.getLocalFileDataData();

        data = new byte[EXTRA_FIELD_HEADER_LENGTH + aLocal.length + EXTRA_FIELD_HEADER_LENGTH + dummyLocal.length];
        writeAsiExtraField();
        writeDummyExtraField(dummyLocal);
    }

    @Test
    void testParseWithSkip() throws Exception {
        ZipExtraField[] ze = ExtraFieldUtils.parse(data, true, ExtraFieldUtils.UnparseableExtraField.SKIP);
        assertEquals(2, ze.length, "number of fields");
        assertAsiDirectoryField(ze[0]);
        assertTrue(ze[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, ze[1].getLocalFileDataLength().getValue(), "data length field 2");

        final byte[] data2 = new byte[data.length - 1];
        System.arraycopy(data, 0, data2, 0, data2.length);

        ze = ExtraFieldUtils.parse(data2, true, ExtraFieldUtils.UnparseableExtraField.SKIP);
        assertEquals(1, ze.length, "number of fields");
        assertAsiDirectoryField(ze[0]);
    }

    private void assertAsiDirectoryField(final ZipExtraField field) {
        assertTrue(field instanceof AsiExtraField, "type field 1");
        assertEquals(EXPECTED_ASI_DIRECTORY_MODE, ((AsiExtraField) field).getMode(), "mode field 1");
    }

    private void writeAsiExtraField() {
        System.arraycopy(a.getHeaderId().getBytes(), 0, data, 0, 2);
        System.arraycopy(a.getLocalFileDataLength().getBytes(), 0, data, 2, 2);
        System.arraycopy(aLocal, 0, data, EXTRA_FIELD_HEADER_LENGTH, aLocal.length);
    }

    private void writeDummyExtraField(final byte[] dummyLocal) {
        final int dummyHeaderOffset = EXTRA_FIELD_HEADER_LENGTH + aLocal.length;
        final int dummyLengthOffset = dummyHeaderOffset + 2;
        final int dummyDataOffset = dummyHeaderOffset + EXTRA_FIELD_HEADER_LENGTH;

        System.arraycopy(dummy.getHeaderId().getBytes(), 0, data, dummyHeaderOffset, 2);
        System.arraycopy(dummy.getLocalFileDataLength().getBytes(), 0, data, dummyLengthOffset, 2);
        System.arraycopy(dummyLocal, 0, data, dummyDataOffset, dummyLocal.length);
    }
}
