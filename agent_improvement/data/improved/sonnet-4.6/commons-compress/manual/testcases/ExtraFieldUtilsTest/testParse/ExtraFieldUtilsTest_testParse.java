package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.zip.ZipException;
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

    /** Size of a ZIP extra field record header: 2 bytes for the ID + 2 bytes for the data length. */
    private static final int EXTRA_FIELD_HEADER_SIZE = 4;

    private AsiExtraField asiField;
    private UnrecognizedExtraField unrecognizedField;

    /** Raw byte array containing both extra fields serialized back-to-back, as they appear in a local file header. */
    private byte[] combinedExtraFieldData;

    /** Raw local-file payload bytes for {@link #asiField}, saved so the test method can compute offsets. */
    private byte[] asiLocalData;

    @BeforeEach
    public void setUp() {
        asiField = new AsiExtraField();
        asiField.setMode(0755);
        asiField.setDirectory(true);

        unrecognizedField = new UnrecognizedExtraField();
        unrecognizedField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedField.setCentralDirectoryData(new byte[] { 0 });

        asiLocalData = asiField.getLocalFileDataData();
        final byte[] unrecognizedLocalData = unrecognizedField.getLocalFileDataData();

        // Serialize both fields into a single byte array:
        // [ asiField header (4 bytes) | asiField data | unrecognizedField header (4 bytes) | unrecognizedField data ]
        combinedExtraFieldData = new byte[EXTRA_FIELD_HEADER_SIZE + asiLocalData.length
                + EXTRA_FIELD_HEADER_SIZE + unrecognizedLocalData.length];

        int offset = 0;
        System.arraycopy(asiField.getHeaderId().getBytes(), 0, combinedExtraFieldData, offset, 2);
        offset += 2;
        System.arraycopy(asiField.getLocalFileDataLength().getBytes(), 0, combinedExtraFieldData, offset, 2);
        offset += 2;
        System.arraycopy(asiLocalData, 0, combinedExtraFieldData, offset, asiLocalData.length);
        offset += asiLocalData.length;

        System.arraycopy(unrecognizedField.getHeaderId().getBytes(), 0, combinedExtraFieldData, offset, 2);
        offset += 2;
        System.arraycopy(unrecognizedField.getLocalFileDataLength().getBytes(), 0, combinedExtraFieldData, offset, 2);
        offset += 2;
        System.arraycopy(unrecognizedLocalData, 0, combinedExtraFieldData, offset, unrecognizedLocalData.length);
    }

    /**
     * test parser.
     */
    @Test
    void testParse() throws Exception {
        // Verify that well-formed data is parsed into the correct field types with the correct values.
        final ZipExtraField[] parsedFields = ExtraFieldUtils.parse(combinedExtraFieldData);
        assertEquals(2, parsedFields.length, "number of fields");
        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");
        assertTrue(parsedFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, parsedFields[1].getLocalFileDataLength().getValue(), "data length field 2");

        // Verify that truncated data (last byte removed) triggers a descriptive exception.
        // The second field starts right after the first field's header + payload.
        final byte[] truncatedData = new byte[combinedExtraFieldData.length - 1];
        System.arraycopy(combinedExtraFieldData, 0, truncatedData, 0, truncatedData.length);

        final int secondFieldOffset = EXTRA_FIELD_HEADER_SIZE + asiLocalData.length;
        final String expectedErrorMessage = "Bad extra field starting at " + secondFieldOffset
                + ".  Block length of 1 bytes exceeds remaining data of 0 bytes.";

        final Exception e = assertThrows(Exception.class,
                () -> ExtraFieldUtils.parse(truncatedData),
                "data should be invalid");
        assertEquals(expectedErrorMessage, e.getMessage(), "message");
    }
}
