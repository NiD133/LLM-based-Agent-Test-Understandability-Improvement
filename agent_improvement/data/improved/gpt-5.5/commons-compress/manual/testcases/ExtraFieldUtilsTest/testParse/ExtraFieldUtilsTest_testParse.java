package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testParse implements UnixStat {

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

    private static final int FIELD_HEADER_LENGTH = 4;
    private static final int FIELD_ID_LENGTH = 2;
    private static final int FIELD_LENGTH_OFFSET = 2;

    private AsiExtraField asiExtraField;

    private UnrecognizedExtraField unrecognizedExtraField;

    private byte[] localExtraFieldData;

    private byte[] asiLocalFileData;

    @BeforeEach
    public void setUp() {
        asiExtraField = createAsiDirectoryExtraField();
        unrecognizedExtraField = createUnrecognizedExtraField();

        asiLocalFileData = asiExtraField.getLocalFileDataData();
        final byte[] unrecognizedLocalFileData = unrecognizedExtraField.getLocalFileDataData();

        localExtraFieldData = new byte[FIELD_HEADER_LENGTH + asiLocalFileData.length + FIELD_HEADER_LENGTH + unrecognizedLocalFileData.length];
        copyFieldHeaderAndData(asiExtraField, asiLocalFileData, localExtraFieldData, 0);
        copyFieldHeaderAndData(unrecognizedExtraField, unrecognizedLocalFileData, localExtraFieldData, FIELD_HEADER_LENGTH + asiLocalFileData.length);
    }

    private AsiExtraField createAsiDirectoryExtraField() {
        final AsiExtraField field = new AsiExtraField();
        field.setMode(0755);
        field.setDirectory(true);
        return field;
    }

    private UnrecognizedExtraField createUnrecognizedExtraField() {
        final UnrecognizedExtraField field = new UnrecognizedExtraField();
        field.setHeaderId(UNRECOGNIZED_HEADER);
        field.setLocalFileDataData(new byte[] { 0 });
        field.setCentralDirectoryData(new byte[] { 0 });
        return field;
    }

    private void copyFieldHeaderAndData(final ZipExtraField field, final byte[] fieldData, final byte[] target, final int offset) {
        System.arraycopy(field.getHeaderId().getBytes(), 0, target, offset, FIELD_ID_LENGTH);
        System.arraycopy(field.getLocalFileDataLength().getBytes(), 0, target, offset + FIELD_LENGTH_OFFSET, FIELD_ID_LENGTH);
        System.arraycopy(fieldData, 0, target, offset + FIELD_HEADER_LENGTH, fieldData.length);
    }

    /**
     * test parser.
     */
    @Test
    void testParse() throws Exception {
        final ZipExtraField[] parsedFields = ExtraFieldUtils.parse(localExtraFieldData);
        assertEquals(2, parsedFields.length, "number of fields");
        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");
        assertTrue(parsedFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, parsedFields[1].getLocalFileDataLength().getValue(), "data length field 2");

        final byte[] truncatedExtraFieldData = new byte[localExtraFieldData.length - 1];
        System.arraycopy(localExtraFieldData, 0, truncatedExtraFieldData, 0, truncatedExtraFieldData.length);
        final Exception e = assertThrows(Exception.class, () -> ExtraFieldUtils.parse(truncatedExtraFieldData), "data should be invalid");
        assertEquals("Bad extra field starting at " + (FIELD_HEADER_LENGTH + asiLocalFileData.length)
                + ".  Block length of 1 bytes exceeds remaining data of 0 bytes.", e.getMessage(), "message");
    }
}
