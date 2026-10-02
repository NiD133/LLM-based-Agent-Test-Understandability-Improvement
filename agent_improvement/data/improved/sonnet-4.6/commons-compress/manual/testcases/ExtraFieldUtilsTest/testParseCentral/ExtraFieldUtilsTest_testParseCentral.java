package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that ExtraFieldUtils.parse correctly identifies and populates known and
 * unknown extra fields when the source is central directory data.
 */
public class ExtraFieldUtilsTest_testParseCentral implements UnixStat {

    /**
     * Header ID for a ZipExtraField not supported by Commons Compress.
     * Uses 0x5555 to avoid conflicting with the Zip64 extra field (ID 0x0001).
     */
    static final ZipShort UNRECOGNIZED_HEADER = new ZipShort(0x5555);

    /**
     * Header ID used for ArrayIndexOutOfBounds-related tests.
     */
    static final ZipShort AIOB_HEADER = new ZipShort(0x1000);

    // Each serialized extra field is prefixed with a 4-byte header:
    //   [2 bytes: header ID] [2 bytes: payload length]
    private static final int EXTRA_FIELD_HEADER_SIZE = 4;

    private AsiExtraField asiField;
    private UnrecognizedExtraField unrecognizedField;

    /** Combined byte array holding two serialized extra fields back-to-back. */
    private byte[] data;

    @BeforeEach
    public void setUp() {
        // Known field: a Unix ASI extra field for a directory with mode 0755
        asiField = new AsiExtraField();
        asiField.setMode(0755);
        asiField.setDirectory(true);

        // Unknown field: unrecognized header with a single zero byte as payload
        unrecognizedField = new UnrecognizedExtraField();
        unrecognizedField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedField.setCentralDirectoryData(new byte[] { 0 });

        // Build the combined byte array: [asiField][unrecognizedField]
        // Each field is serialized as: header-ID (2 bytes) + length (2 bytes) + payload (N bytes)
        final byte[] asiPayload = asiField.getLocalFileDataData();
        final byte[] unrecognizedPayload = unrecognizedField.getLocalFileDataData();

        data = new byte[EXTRA_FIELD_HEADER_SIZE + asiPayload.length
                + EXTRA_FIELD_HEADER_SIZE + unrecognizedPayload.length];

        int offset = 0;

        // Serialize AsiExtraField: header ID + payload length + payload bytes
        System.arraycopy(asiField.getHeaderId().getBytes(), 0, data, offset, 2);
        System.arraycopy(asiField.getLocalFileDataLength().getBytes(), 0, data, offset + 2, 2);
        System.arraycopy(asiPayload, 0, data, offset + EXTRA_FIELD_HEADER_SIZE, asiPayload.length);
        offset += EXTRA_FIELD_HEADER_SIZE + asiPayload.length;

        // Serialize UnrecognizedExtraField: header ID + payload length + payload bytes
        System.arraycopy(unrecognizedField.getHeaderId().getBytes(), 0, data, offset, 2);
        System.arraycopy(unrecognizedField.getLocalFileDataLength().getBytes(), 0, data, offset + 2, 2);
        System.arraycopy(unrecognizedPayload, 0, data, offset + EXTRA_FIELD_HEADER_SIZE, unrecognizedPayload.length);
    }

    @Test
    void testParseCentral() throws Exception {
        // Parse the byte array as central directory data (local = false)
        final boolean isCentralDirectory = false;
        final ZipExtraField[] parsedFields = ExtraFieldUtils.parse(data, isCentralDirectory);

        assertEquals(2, parsedFields.length, "number of fields");

        // First field: recognized as AsiExtraField with directory mode 0755
        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");

        // Second field: falls back to UnrecognizedExtraField with 1 byte of central directory data
        assertTrue(parsedFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, parsedFields[1].getCentralDirectoryLength().getValue(), "data length field 2");
    }
}
