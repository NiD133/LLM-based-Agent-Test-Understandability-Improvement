package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ExtraFieldUtils#parse} honours the
 * {@link ExtraFieldUtils.UnparseableExtraField#SKIP SKIP} behaviour: a trailing
 * extra field whose declared length runs past the end of the available data is
 * silently dropped instead of causing a failure.
 */
public class ExtraFieldUtilsTest_testParseWithSkip implements UnixStat {

    /** Number of bytes in an extra field header: 2 for the id + 2 for the length. */
    private static final int HEADER_LENGTH = 4;

    /**
     * Header-ID of a ZipExtraField not supported by Commons Compress.
     *
     * <p>
     * Used to be ZipShort(1) but this is the ID of the Zip64 extra field.
     * </p>
     */
    static final ZipShort UNRECOGNIZED_HEADER = new ZipShort(0x5555);

    /** A recognized field (directory, mode 0755) used as the first field in the data. */
    private AsiExtraField asiField;

    /** An unrecognized field used as the second (trailing) field in the data. */
    private UnrecognizedExtraField unrecognizedField;

    /** Serialized local-file-data form of {@link #asiField} followed by {@link #unrecognizedField}. */
    private byte[] data;

    @BeforeEach
    public void setUp() {
        asiField = new AsiExtraField();
        asiField.setMode(0755);
        asiField.setDirectory(true);

        unrecognizedField = new UnrecognizedExtraField();
        unrecognizedField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedField.setCentralDirectoryData(new byte[] { 0 });

        data = serializeLocalFileData(asiField, unrecognizedField);
    }

    @Test
    void testParseWithSkip() throws Exception {
        // With the data fully intact, both fields parse successfully.
        ZipExtraField[] fields = ExtraFieldUtils.parse(data, true, ExtraFieldUtils.UnparseableExtraField.SKIP);

        assertEquals(2, fields.length, "number of fields");
        assertTrue(fields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) fields[0]).getMode(), "mode field 1");
        assertTrue(fields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, fields[1].getLocalFileDataLength().getValue(), "data length field 2");

        // Drop the final byte so the second field's declared length overruns the data.
        // With SKIP, that unparseable trailing field is dropped and only the first remains.
        final byte[] truncatedData = new byte[data.length - 1];
        System.arraycopy(data, 0, truncatedData, 0, truncatedData.length);

        fields = ExtraFieldUtils.parse(truncatedData, true, ExtraFieldUtils.UnparseableExtraField.SKIP);

        assertEquals(1, fields.length, "number of fields");
        assertTrue(fields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) fields[0]).getMode(), "mode field 1");
    }

    /**
     * Concatenates the local-file-data representation of each field, mirroring the byte
     * layout {@link ExtraFieldUtils#parse} expects: header id (2 bytes), local data length
     * (2 bytes), then the local data itself.
     */
    private static byte[] serializeLocalFileData(final ZipExtraField... fields) {
        int totalLength = 0;
        for (final ZipExtraField field : fields) {
            totalLength += HEADER_LENGTH + field.getLocalFileDataData().length;
        }

        final byte[] result = new byte[totalLength];
        int offset = 0;
        for (final ZipExtraField field : fields) {
            final byte[] localData = field.getLocalFileDataData();
            System.arraycopy(field.getHeaderId().getBytes(), 0, result, offset, 2);
            System.arraycopy(field.getLocalFileDataLength().getBytes(), 0, result, offset + 2, 2);
            System.arraycopy(localData, 0, result, offset + HEADER_LENGTH, localData.length);
            offset += HEADER_LENGTH + localData.length;
        }
        return result;
    }
}
