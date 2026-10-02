package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testParseWithRead implements UnixStat {

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

    private static final int ZIP_EXTRA_FIELD_HEADER_LENGTH = 4;
    private static final int EXPECTED_DIRECTORY_MODE = 040755;

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

        data = new byte[ZIP_EXTRA_FIELD_HEADER_LENGTH + aLocal.length + ZIP_EXTRA_FIELD_HEADER_LENGTH + dummyLocal.length];
        final int asiFieldOffset = 0;
        final int dummyFieldOffset = ZIP_EXTRA_FIELD_HEADER_LENGTH + aLocal.length;

        writeExtraField(a.getHeaderId(), a.getLocalFileDataLength(), aLocal, asiFieldOffset);
        writeExtraField(dummy.getHeaderId(), dummy.getLocalFileDataLength(), dummyLocal, dummyFieldOffset);
    }

    @Test
    void testParseWithRead() throws Exception {
        ZipExtraField[] ze = ExtraFieldUtils.parse(data, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertEquals(2, ze.length, "number of fields");
        assertAsiDirectoryField(ze[0]);
        assertTrue(ze[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, ze[1].getLocalFileDataLength().getValue(), "data length field 2");

        final byte[] data2 = new byte[data.length - 1];
        System.arraycopy(data, 0, data2, 0, data2.length);

        ze = ExtraFieldUtils.parse(data2, true, ExtraFieldUtils.UnparseableExtraField.READ);
        assertEquals(2, ze.length, "number of fields");
        assertAsiDirectoryField(ze[0]);
        assertTrue(ze[1] instanceof UnparseableExtraFieldData, "type field 2");
        assertEquals(4, ze[1].getLocalFileDataLength().getValue(), "data length field 2");
        assertUnparseableFieldContainsTrailingBytesFrom(data2, ze[1]);
    }

    private void assertAsiDirectoryField(final ZipExtraField field) {
        assertTrue(field instanceof AsiExtraField, "type field 1");
        assertEquals(EXPECTED_DIRECTORY_MODE, ((AsiExtraField) field).getMode(), "mode field 1");
    }

    private void assertUnparseableFieldContainsTrailingBytesFrom(final byte[] truncatedData, final ZipExtraField unparseableField) {
        final byte[] unparseableData = unparseableField.getLocalFileDataData();
        final int unparseableDataOffset = data.length - 5;
        for (int i = 0; i < 4; i++) {
            assertEquals(truncatedData[unparseableDataOffset + i], unparseableData[i], "byte number " + i);
        }
    }

    private void writeExtraField(final ZipShort headerId, final ZipShort localFileDataLength, final byte[] localFileData, final int offset) {
        System.arraycopy(headerId.getBytes(), 0, data, offset, 2);
        System.arraycopy(localFileDataLength.getBytes(), 0, data, offset + 2, 2);
        System.arraycopy(localFileData, 0, data, offset + ZIP_EXTRA_FIELD_HEADER_LENGTH, localFileData.length);
    }
}
