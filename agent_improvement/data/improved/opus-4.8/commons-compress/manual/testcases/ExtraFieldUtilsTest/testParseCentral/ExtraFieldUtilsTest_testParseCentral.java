package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ExtraFieldUtils#parse(byte[], boolean)} correctly splits a
 * raw central-directory extra-field block into its individual {@link ZipExtraField}s.
 */
public class ExtraFieldUtilsTest_testParseCentral implements UnixStat {

    /**
     * Header-ID of a ZipExtraField not supported by Commons Compress.
     *
     * <p>
     * Used to be ZipShort(1) but this is the ID of the Zip64 extra field.
     * </p>
     */
    static final ZipShort UNRECOGNIZED_HEADER = new ZipShort(0x5555);

    /** Number of bytes used to encode a header-ID or a data-length field. */
    private static final int SHORT_SIZE = 2;

    /**
     * A recognized extra field: a directory entry with Unix mode 0755. When parsed
     * back, Commons Compress restores it to an {@link AsiExtraField}.
     */
    private AsiExtraField asiField;

    /**
     * An unrecognized extra field carrying a single data byte. When parsed back,
     * Commons Compress represents it as an {@link UnrecognizedExtraField}.
     */
    private UnrecognizedExtraField unrecognizedField;

    /** The two fields above serialized into a single raw extra-field block. */
    private byte[] extraFieldBlock;

    @BeforeEach
    public void setUp() {
        asiField = new AsiExtraField();
        asiField.setMode(0755);
        asiField.setDirectory(true);

        unrecognizedField = new UnrecognizedExtraField();
        unrecognizedField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedField.setCentralDirectoryData(new byte[] { 0 });

        extraFieldBlock = serializeAsLocalFileData(asiField, unrecognizedField);
    }

    /**
     * Serializes the given fields into the on-disk extra-field layout, where each
     * field is laid out as: 2-byte header-ID, 2-byte data length, then the data.
     */
    private static byte[] serializeAsLocalFileData(final AsiExtraField first, final UnrecognizedExtraField second) {
        final byte[] firstData = first.getLocalFileDataData();
        final byte[] secondData = second.getLocalFileDataData();

        final byte[] block = new byte[SHORT_SIZE + SHORT_SIZE + firstData.length + SHORT_SIZE + SHORT_SIZE + secondData.length];
        int offset = 0;

        offset = append(block, offset, first.getHeaderId().getBytes());
        offset = append(block, offset, first.getLocalFileDataLength().getBytes());
        offset = append(block, offset, firstData);
        offset = append(block, offset, second.getHeaderId().getBytes());
        offset = append(block, offset, second.getLocalFileDataLength().getBytes());
        append(block, offset, secondData);

        return block;
    }

    /** Copies {@code source} into {@code target} at {@code offset} and returns the new offset. */
    private static int append(final byte[] target, final int offset, final byte[] source) {
        System.arraycopy(source, 0, target, offset, source.length);
        return offset + source.length;
    }

    @Test
    void testParseCentral() throws Exception {
        final ZipExtraField[] parsedFields = ExtraFieldUtils.parse(extraFieldBlock, false);

        assertEquals(2, parsedFields.length, "number of fields");

        assertTrue(parsedFields[0] instanceof AsiExtraField, "type field 1");
        assertEquals(040755, ((AsiExtraField) parsedFields[0]).getMode(), "mode field 1");

        assertTrue(parsedFields[1] instanceof UnrecognizedExtraField, "type field 2");
        assertEquals(1, parsedFields[1].getCentralDirectoryLength().getValue(), "data length field 2");
    }
}
