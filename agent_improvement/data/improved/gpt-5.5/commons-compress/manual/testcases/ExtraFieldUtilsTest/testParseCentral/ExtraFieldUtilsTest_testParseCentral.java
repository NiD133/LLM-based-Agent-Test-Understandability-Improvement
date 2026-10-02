package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testParseCentral implements UnixStat {

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
    private static final int EXPECTED_FIELD_COUNT = 2;
    private static final int EXPECTED_DIRECTORY_MODE = 040755;
    private static final int EXPECTED_UNRECOGNIZED_FIELD_LENGTH = 1;

    private AsiExtraField asiExtraField;
    private UnrecognizedExtraField unrecognizedExtraField;
    private byte[] centralDirectoryData;
    private byte[] asiLocalFileData;

    @BeforeEach
    public void setUp() {
        asiExtraField = new AsiExtraField();
        asiExtraField.setMode(0755);
        asiExtraField.setDirectory(true);

        unrecognizedExtraField = new UnrecognizedExtraField();
        unrecognizedExtraField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedExtraField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedExtraField.setCentralDirectoryData(new byte[] { 0 });

        asiLocalFileData = asiExtraField.getLocalFileDataData();
        final byte[] unrecognizedLocalFileData = unrecognizedExtraField.getLocalFileDataData();
        centralDirectoryData = new byte[FIELD_HEADER_LENGTH + asiLocalFileData.length + FIELD_HEADER_LENGTH + unrecognizedLocalFileData.length];

        int offset = 0;
        offset = appendLocalFileData(asiExtraField, asiLocalFileData, centralDirectoryData, offset);
        appendLocalFileData(unrecognizedExtraField, unrecognizedLocalFileData, centralDirectoryData, offset);
    }

    @Test
    void testParseCentral() throws Exception {
        final ZipExtraField[] parsedFields = ExtraFieldUtils.parse(centralDirectoryData, false);

        assertEquals(EXPECTED_FIELD_COUNT, parsedFields.length, "number of fields");
        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(EXPECTED_DIRECTORY_MODE, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");
        assertTrue(parsedFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(EXPECTED_UNRECOGNIZED_FIELD_LENGTH, parsedFields[1].getCentralDirectoryLength().getValue(), "data length field 2");
    }

    private static int appendLocalFileData(final ZipExtraField field, final byte[] localFileData, final byte[] target, final int offset) {
        System.arraycopy(field.getHeaderId().getBytes(), 0, target, offset, 2);
        System.arraycopy(field.getLocalFileDataLength().getBytes(), 0, target, offset + 2, 2);
        System.arraycopy(localFileData, 0, target, offset + FIELD_HEADER_LENGTH, localFileData.length);
        return offset + FIELD_HEADER_LENGTH + localFileData.length;
    }
}
