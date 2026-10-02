package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link ExtraFieldUtils#parse} when told to {@code READ} extra-field
 * blocks that it cannot fully parse.
 *
 * <p>
 * Each ZIP extra field is laid out as:
 * </p>
 *
 * <pre>
 *   [ header id : 2 bytes ][ data length : 2 bytes ][ data : &lt;data length&gt; bytes ]
 * </pre>
 *
 * <p>
 * The test data concatenates two such fields back to back:
 * </p>
 * <ol>
 *   <li>a recognized {@link AsiExtraField} (a Unix-permissions field), and</li>
 *   <li>an unrecognized field whose header id is not known to Commons Compress.</li>
 * </ol>
 */
public class ExtraFieldUtilsTest_testParseWithRead {

    /** Size in bytes of the header-id portion of an extra field. */
    private static final int HEADER_ID_SIZE = 2;

    /** Size in bytes of the data-length portion of an extra field. */
    private static final int DATA_LENGTH_SIZE = 2;

    /** Size in bytes of an extra field's fixed prefix (header id + data length). */
    private static final int FIELD_PREFIX_SIZE = HEADER_ID_SIZE + DATA_LENGTH_SIZE;

    /**
     * Header-id of a {@link ZipExtraField} that Commons Compress does not support.
     *
     * <p>
     * Used to be {@code ZipShort(1)}, but that is the id of the Zip64 extra field.
     * </p>
     */
    private static final ZipShort UNRECOGNIZED_HEADER = new ZipShort(0x5555);

    /** Expected Unix mode of the directory entry: octal 0755 with the directory flag set. */
    private static final int EXPECTED_DIRECTORY_MODE = 040755;

    /** Concatenated local-file-data bytes of {@link #asiField} followed by {@link #unrecognizedField}. */
    private byte[] extraFieldData;

    /** A recognized extra field: a directory with Unix permissions 0755. */
    private AsiExtraField asiField;

    /** An extra field whose header id is unknown to Commons Compress. */
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

        extraFieldData = concatenateAsLocalFileData(asiField, unrecognizedField);
    }

    /**
     * Serializes the given fields into a single byte array using the local-file-data
     * layout ({@code header id | data length | data} per field).
     */
    private static byte[] concatenateAsLocalFileData(final ZipExtraField... fields) {
        int totalSize = 0;
        for (final ZipExtraField field : fields) {
            totalSize += FIELD_PREFIX_SIZE + field.getLocalFileDataData().length;
        }

        final byte[] buffer = new byte[totalSize];
        int offset = 0;
        for (final ZipExtraField field : fields) {
            final byte[] fieldData = field.getLocalFileDataData();
            System.arraycopy(field.getHeaderId().getBytes(), 0, buffer, offset, HEADER_ID_SIZE);
            System.arraycopy(field.getLocalFileDataLength().getBytes(), 0, buffer, offset + HEADER_ID_SIZE, DATA_LENGTH_SIZE);
            System.arraycopy(fieldData, 0, buffer, offset + FIELD_PREFIX_SIZE, fieldData.length);
            offset += FIELD_PREFIX_SIZE + fieldData.length;
        }
        return buffer;
    }

    @Test
    void testParseWithRead() throws Exception {
        // Complete data: both fields are well-formed, so the unrecognized field is
        // returned as an UnrecognizedExtraField holding its single data byte.
        ZipExtraField[] parsed = ExtraFieldUtils.parse(extraFieldData, true, ExtraFieldUtils.UnparseableExtraField.READ);

        assertEquals(2, parsed.length, "number of fields");
        assertInstanceOf(AsiExtraField.class, parsed[0], "type field 1");
        assertEquals(EXPECTED_DIRECTORY_MODE, ((AsiExtraField) parsed[0]).getMode(), "mode field 1");
        assertInstanceOf(UnrecognizedExtraField.class, parsed[1], "type field 2");
        assertEquals(1, parsed[1].getLocalFileDataLength().getValue(), "data length field 2");

        // Truncated data: drop the very last byte so the second field's declared length
        // exceeds the bytes actually present. With READ, the leftover bytes are captured
        // verbatim as an UnparseableExtraFieldData instead of being parsed.
        final byte[] truncatedData = new byte[extraFieldData.length - 1];
        System.arraycopy(extraFieldData, 0, truncatedData, 0, truncatedData.length);

        parsed = ExtraFieldUtils.parse(truncatedData, true, ExtraFieldUtils.UnparseableExtraField.READ);

        assertEquals(2, parsed.length, "number of fields");
        assertInstanceOf(AsiExtraField.class, parsed[0], "type field 1");
        assertEquals(EXPECTED_DIRECTORY_MODE, ((AsiExtraField) parsed[0]).getMode(), "mode field 1");
        assertInstanceOf(UnparseableExtraFieldData.class, parsed[1], "type field 2");

        // The unparseable holder keeps the 4 trailing bytes (header id + length of the
        // truncated second field) exactly as they appeared in the input.
        final int unparseableByteCount = 4;
        assertEquals(unparseableByteCount, parsed[1].getLocalFileDataLength().getValue(), "data length field 2");
        final int unparseableStart = truncatedData.length - unparseableByteCount;
        for (int i = 0; i < unparseableByteCount; i++) {
            assertEquals(truncatedData[unparseableStart + i], parsed[1].getLocalFileDataData()[i], "byte number " + i);
        }
    }
}
