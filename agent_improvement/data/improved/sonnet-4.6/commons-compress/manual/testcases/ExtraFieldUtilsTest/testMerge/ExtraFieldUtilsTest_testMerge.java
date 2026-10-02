package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testMerge implements UnixStat {

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

    /** Number of bytes used by the header-ID and length fields that precede each extra field's payload. */
    private static final int EXTRA_FIELD_HEADER_SIZE = 4;

    private AsiExtraField asiField;
    private UnrecognizedExtraField unrecognizedField;

    /** Expected binary encoding of both fields in local-file-data format. */
    private byte[] expectedLocalData;

    /** Local-file-data payload of {@code asiField}, cached so the central-directory test can reuse it. */
    private byte[] asiLocalPayload;

    @BeforeEach
    public void setUp() {
        asiField = new AsiExtraField();
        asiField.setMode(0755);
        asiField.setDirectory(true);

        unrecognizedField = new UnrecognizedExtraField();
        unrecognizedField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedField.setCentralDirectoryData(new byte[] { 0 });

        asiLocalPayload = asiField.getLocalFileDataData();
        final byte[] unrecognizedLocalPayload = unrecognizedField.getLocalFileDataData();

        // Layout: [headerId(2)] [length(2)] [payload(n)]  repeated for each field
        expectedLocalData = new byte[EXTRA_FIELD_HEADER_SIZE + asiLocalPayload.length
                + EXTRA_FIELD_HEADER_SIZE + unrecognizedLocalPayload.length];

        int offset = 0;
        System.arraycopy(asiField.getHeaderId().getBytes(), 0, expectedLocalData, offset, 2);
        System.arraycopy(asiField.getLocalFileDataLength().getBytes(), 0, expectedLocalData, offset + 2, 2);
        offset += EXTRA_FIELD_HEADER_SIZE;
        System.arraycopy(asiLocalPayload, 0, expectedLocalData, offset, asiLocalPayload.length);
        offset += asiLocalPayload.length;

        System.arraycopy(unrecognizedField.getHeaderId().getBytes(), 0, expectedLocalData, offset, 2);
        System.arraycopy(unrecognizedField.getLocalFileDataLength().getBytes(), 0, expectedLocalData, offset + 2, 2);
        offset += EXTRA_FIELD_HEADER_SIZE;
        System.arraycopy(unrecognizedLocalPayload, 0, expectedLocalData, offset, unrecognizedLocalPayload.length);
    }

    /**
     * Verifies that mergeLocalFileDataData and mergeCentralDirectoryData correctly
     * serialise an array of ZipExtraFields into the expected binary format.
     */
    @Test
    void testMerge() {
        final ZipExtraField[] fields = { asiField, unrecognizedField };

        // --- local file data ---
        final byte[] localMerged = ExtraFieldUtils.mergeLocalFileDataData(fields);
        assertEquals(expectedLocalData.length, localMerged.length, "local length");
        assertArrayEquals(expectedLocalData, localMerged, "local data");

        // --- central directory data ---
        // Same structure as local, but the unrecognized field uses its central-directory length/payload.
        final byte[] unrecognizedCentralPayload = unrecognizedField.getCentralDirectoryData();
        final byte[] expectedCentralData = new byte[EXTRA_FIELD_HEADER_SIZE + asiLocalPayload.length
                + EXTRA_FIELD_HEADER_SIZE + unrecognizedCentralPayload.length];

        // Copy the ASI field header + payload unchanged (local and central are identical for AsiExtraField).
        System.arraycopy(expectedLocalData, 0, expectedCentralData, 0,
                EXTRA_FIELD_HEADER_SIZE + asiLocalPayload.length + 2);
        // Replace the unrecognized field's length with its central-directory length.
        System.arraycopy(unrecognizedField.getCentralDirectoryLength().getBytes(), 0, expectedCentralData,
                EXTRA_FIELD_HEADER_SIZE + asiLocalPayload.length + 2, 2);
        System.arraycopy(unrecognizedCentralPayload, 0, expectedCentralData,
                EXTRA_FIELD_HEADER_SIZE + asiLocalPayload.length + EXTRA_FIELD_HEADER_SIZE,
                unrecognizedCentralPayload.length);

        final byte[] centralMerged = ExtraFieldUtils.mergeCentralDirectoryData(fields);
        assertEquals(expectedCentralData.length, centralMerged.length, "central length");
        assertArrayEquals(expectedCentralData, centralMerged, "central data");
    }
}
