package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ExtraFieldUtils#mergeLocalFileDataData} and
 * {@link ExtraFieldUtils#mergeCentralDirectoryData}.
 *
 * <p>
 * Both merge methods serialize an array of extra fields back into the raw byte
 * layout used inside a ZIP entry. For every field the layout is:
 * </p>
 *
 * <pre>
 *   header-id (2 bytes) | data-length (2 bytes) | data (data-length bytes)
 * </pre>
 *
 * <p>
 * The tests build the expected byte layout by hand and compare it against the
 * output of the merge methods.
 * </p>
 */
public class ExtraFieldUtilsTest_testMerge {

    /**
     * Header-ID of a ZipExtraField not supported by Commons Compress.
     *
     * <p>
     * Used to be ZipShort(1) but this is the ID of the Zip64 extra field.
     * </p>
     */
    static final ZipShort UNRECOGNIZED_HEADER = new ZipShort(0x5555);

    /** A recognized extra field whose local and central data are identical. */
    private AsiExtraField asiField;

    /** An unrecognized extra field with distinct local and central data. */
    private UnrecognizedExtraField unrecognizedField;

    @BeforeEach
    public void setUp() {
        asiField = new AsiExtraField();
        asiField.setMode(0755);
        asiField.setDirectory(true);

        unrecognizedField = new UnrecognizedExtraField();
        unrecognizedField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedField.setCentralDirectoryData(new byte[] { 0 });
    }

    /**
     * Test merge methods
     */
    @Test
    void testMerge() {
        final ZipExtraField[] fields = { asiField, unrecognizedField };

        // mergeLocalFileDataData serializes each field using its LOCAL data.
        final byte[] expectedLocal = concat(
            serializeLocal(asiField),
            serializeLocal(unrecognizedField));
        final byte[] actualLocal = ExtraFieldUtils.mergeLocalFileDataData(fields);
        assertByteArrayEquals("local", expectedLocal, actualLocal);

        // mergeCentralDirectoryData serializes each field using its CENTRAL data.
        final byte[] expectedCentral = concat(
            serializeCentral(asiField),
            serializeCentral(unrecognizedField));
        final byte[] actualCentral = ExtraFieldUtils.mergeCentralDirectoryData(fields);
        assertByteArrayEquals("central", expectedCentral, actualCentral);
    }

    /** Serializes a field as it appears in the local file header. */
    private static byte[] serializeLocal(final ZipExtraField field) {
        return serialize(field.getHeaderId(), field.getLocalFileDataLength(), field.getLocalFileDataData());
    }

    /** Serializes a field as it appears in the central directory. */
    private static byte[] serializeCentral(final ZipExtraField field) {
        return serialize(field.getHeaderId(), field.getCentralDirectoryLength(), field.getCentralDirectoryData());
    }

    /**
     * Builds the {@code header-id | data-length | data} byte layout for a single
     * extra field.
     */
    private static byte[] serialize(final ZipShort headerId, final ZipShort dataLength, final byte[] data) {
        final byte[] result = new byte[2 + 2 + data.length];
        System.arraycopy(headerId.getBytes(), 0, result, 0, 2);
        System.arraycopy(dataLength.getBytes(), 0, result, 2, 2);
        System.arraycopy(data, 0, result, 4, data.length);
        return result;
    }

    /** Concatenates the serialized layouts of all fields into one array. */
    private static byte[] concat(final byte[]... parts) {
        int totalLength = 0;
        for (final byte[] part : parts) {
            totalLength += part.length;
        }
        final byte[] result = new byte[totalLength];
        int offset = 0;
        for (final byte[] part : parts) {
            System.arraycopy(part, 0, result, offset, part.length);
            offset += part.length;
        }
        return result;
    }

    /** Asserts that two byte arrays have the same length and identical contents. */
    private static void assertByteArrayEquals(final String label, final byte[] expected, final byte[] actual) {
        assertEquals(expected.length, actual.length, label + " length");
        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i], label + " byte " + i);
        }
    }
}
