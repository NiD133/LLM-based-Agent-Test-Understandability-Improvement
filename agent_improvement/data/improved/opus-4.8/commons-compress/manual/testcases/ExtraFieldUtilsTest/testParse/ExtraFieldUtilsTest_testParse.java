package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ExtraFieldUtils#parse(byte[])}.
 *
 * <p>
 * The local-file-data byte layout this test exercises consists of two
 * consecutive extra fields, each laid out as:
 * </p>
 *
 * <pre>
 *   [ header-id : 2 bytes ][ data-length : 2 bytes ][ payload : data-length bytes ]
 * </pre>
 */
public class ExtraFieldUtilsTest_testParse implements UnixStat {

    /** Number of bytes that precede each extra field's payload (2-byte header id + 2-byte length). */
    private static final int FIELD_HEADER_LENGTH = 4;

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

    /** First field in the serialized data: a recognized ASI extra field describing a directory with mode 0755. */
    private AsiExtraField asiField;

    /** Second field in the serialized data: an unrecognized field with a single-byte payload. */
    private UnrecognizedExtraField unrecognizedField;

    /** The serialized local-file-data containing both fields, fed to {@link ExtraFieldUtils#parse(byte[])}. */
    private byte[] data;

    /** Offset within {@link #data} at which the second (unrecognized) field begins. */
    private int unrecognizedFieldOffset;

    @BeforeEach
    public void setUp() {
        asiField = new AsiExtraField();
        asiField.setMode(0755);
        asiField.setDirectory(true);

        unrecognizedField = new UnrecognizedExtraField();
        unrecognizedField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedField.setCentralDirectoryData(new byte[] { 0 });

        final byte[] asiPayload = asiField.getLocalFileDataData();
        final byte[] unrecognizedPayload = unrecognizedField.getLocalFileDataData();

        // The second field starts right after the first field's header and payload.
        unrecognizedFieldOffset = FIELD_HEADER_LENGTH + asiPayload.length;

        data = new byte[FIELD_HEADER_LENGTH + asiPayload.length + FIELD_HEADER_LENGTH + unrecognizedPayload.length];

        // First field: ASI extra field.
        System.arraycopy(asiField.getHeaderId().getBytes(), 0, data, 0, 2);
        System.arraycopy(asiField.getLocalFileDataLength().getBytes(), 0, data, 2, 2);
        System.arraycopy(asiPayload, 0, data, FIELD_HEADER_LENGTH, asiPayload.length);

        // Second field: unrecognized extra field.
        System.arraycopy(unrecognizedField.getHeaderId().getBytes(), 0, data, unrecognizedFieldOffset, 2);
        System.arraycopy(unrecognizedField.getLocalFileDataLength().getBytes(), 0, data, unrecognizedFieldOffset + 2, 2);
        System.arraycopy(unrecognizedPayload, 0, data, unrecognizedFieldOffset + FIELD_HEADER_LENGTH, unrecognizedPayload.length);
    }

    /**
     * Parsing well-formed data yields both fields; truncating the last byte makes the
     * unrecognized field's declared length exceed the remaining data, which must fail.
     */
    @Test
    void testParse() throws Exception {
        final ZipExtraField[] parsedFields = ExtraFieldUtils.parse(data);

        assertEquals(2, parsedFields.length, "number of fields");

        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");

        assertTrue(parsedFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, parsedFields[1].getLocalFileDataLength().getValue(), "data length field 2");

        // Drop the final payload byte so the unrecognized field claims more data than is present.
        final byte[] truncatedData = new byte[data.length - 1];
        System.arraycopy(data, 0, truncatedData, 0, truncatedData.length);

        final Exception e = assertThrows(Exception.class, () -> ExtraFieldUtils.parse(truncatedData), "data should be invalid");
        assertEquals("Bad extra field starting at " + unrecognizedFieldOffset + ".  Block length of 1 bytes exceeds remaining data of 0 bytes.",
                e.getMessage(), "message");
    }
}
