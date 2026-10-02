package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests that mergeLocalFileDataData and mergeCentralDirectoryData correctly handle
 * an array of extra fields whose last element is an UnparseableExtraFieldData instance.
 *
 * <p>When the last field is UnparseableExtraFieldData the merge methods omit its
 * 4-byte header (ID + length) and append only its raw payload bytes.</p>
 */
public class ExtraFieldUtilsTest_testMergeWithUnparseableData implements UnixStat {

    /**
     * Header ID that Commons Compress does not recognise (avoids collision with the
     * well-known Zip64 header 0x0001).
     */
    static final ZipShort UNRECOGNIZED_HEADER = new ZipShort(0x5555);

    /**
     * Header ID used for the ArrayIndexOutOfBounds edge-case variant of the test.
     */
    static final ZipShort AIOB_HEADER = new ZipShort(0x1000);

    // -----------------------------------------------------------------------
    // Fixtures set up in setUp()
    // -----------------------------------------------------------------------

    /** A well-known, parseable extra field used as the first field in every merge. */
    private AsiExtraField asiField;

    /**
     * Reference byte array representing the fully-serialised local-file-data of
     * both {@code asiField} and a one-byte dummy field with {@link #UNRECOGNIZED_HEADER}.
     *
     * <p>Layout (each field has a 4-byte header: 2-byte ID + 2-byte length):
     * <pre>
     *   [ AsiField header (4) | AsiField payload (aLocal.length) |
     *     DummyField header (4) | DummyField payload (1) ]
     * </pre>
     * </p>
     */
    private byte[] referenceData;

    /**
     * Raw payload bytes of {@code asiField}'s local-file-data section (no header).
     * Kept as a field so the central-directory assertion can reuse it.
     */
    private byte[] asiLocalPayload;

    @BeforeEach
    public void setUp() {
        // --- AsiExtraField: a parseable field with a known header ---
        asiField = new AsiExtraField();
        asiField.setMode(0755);
        asiField.setDirectory(true);
        asiLocalPayload = asiField.getLocalFileDataData();

        // --- Dummy field: an unrecognised field with one payload byte ---
        UnrecognizedExtraField dummyField = new UnrecognizedExtraField();
        dummyField.setHeaderId(UNRECOGNIZED_HEADER);
        dummyField.setLocalFileDataData(new byte[] { 0 });
        dummyField.setCentralDirectoryData(new byte[] { 0 });
        byte[] dummyLocalPayload = dummyField.getLocalFileDataData();

        // Build the reference byte array with both fields fully serialised
        // (each field prefixed with its 4-byte header).
        referenceData = new byte[4 + asiLocalPayload.length + 4 + dummyLocalPayload.length];
        int pos = 0;

        // AsiField: header (ID + length) then payload
        System.arraycopy(asiField.getHeaderId().getBytes(),           0, referenceData, pos,     2); pos += 2;
        System.arraycopy(asiField.getLocalFileDataLength().getBytes(), 0, referenceData, pos,     2); pos += 2;
        System.arraycopy(asiLocalPayload,                              0, referenceData, pos,     asiLocalPayload.length);
        pos += asiLocalPayload.length;

        // DummyField: header (ID + length) then payload
        System.arraycopy(dummyField.getHeaderId().getBytes(),           0, referenceData, pos,   2); pos += 2;
        System.arraycopy(dummyField.getLocalFileDataLength().getBytes(), 0, referenceData, pos,   2); pos += 2;
        System.arraycopy(dummyLocalPayload,                              0, referenceData, pos,   dummyLocalPayload.length);
    }

    /**
     * Verifies that merging an array whose last element is an {@link UnparseableExtraFieldData}
     * produces the correct byte sequences for both the local-file-data and central-directory
     * sections.
     *
     * <p>The {@code UnparseableExtraFieldData} field is created by parsing 4 raw bytes
     * ({@link #UNRECOGNIZED_HEADER} + a claimed length of 1) where the claimed length
     * exceeds the remaining data, making it genuinely unparseable. The merge methods
     * must therefore <em>omit</em> the 4-byte header and append only the raw payload.</p>
     */
    @Test
    void testMergeWithUnparseableData() throws Exception {
        // Build an UnparseableExtraFieldData by feeding it raw bytes whose claimed
        // payload length (1 byte) exceeds the available data (0 bytes after the header).
        // This mirrors what ExtraFieldUtils.parse() would produce for truncated extra data.
        byte[] unrecognizedHeaderBytes = UNRECOGNIZED_HEADER.getBytes();
        UnparseableExtraFieldData unparseableField = new UnparseableExtraFieldData();
        unparseableField.parseFromLocalFileData(
                new byte[] { unrecognizedHeaderBytes[0], unrecognizedHeaderBytes[1], 1, 0 },
                /* offset */ 0, /* length */ 4);

        // --- Assert local-file-data merge ---
        // Expected: asiField's serialised bytes + unparseableField's raw payload (no header).
        // That is one byte shorter than referenceData (which has a full 4-byte header for
        // the dummy field but only a 1-byte payload, while unparseableField stores 4 raw
        // bytes without a separate header).
        byte[] mergedLocalData = ExtraFieldUtils.mergeLocalFileDataData(
                new ZipExtraField[] { asiField, unparseableField });

        assertEquals(referenceData.length - 1, mergedLocalData.length, "merged local-file-data length");
        assertArrayEquals(
                java.util.Arrays.copyOf(referenceData, mergedLocalData.length),
                mergedLocalData,
                "merged local-file-data bytes");

        // --- Assert central-directory merge ---
        // The central-directory representation of the unparseable field may differ from
        // its local representation. Build the expected central bytes from the reference
        // up to the end of asiField, then append the unparseable field's central payload.
        byte[] unparseableCentralPayload = unparseableField.getCentralDirectoryData();
        int asiSectionLength = 4 + asiLocalPayload.length; // header (4) + payload
        byte[] expectedCentralData = new byte[asiSectionLength + unparseableCentralPayload.length];
        System.arraycopy(referenceData,            0, expectedCentralData, 0,               asiSectionLength + 2);
        System.arraycopy(unparseableCentralPayload, 0, expectedCentralData, asiSectionLength, unparseableCentralPayload.length);

        byte[] mergedCentralData = ExtraFieldUtils.mergeCentralDirectoryData(
                new ZipExtraField[] { asiField, unparseableField });

        assertEquals(expectedCentralData.length, mergedCentralData.length, "merged central-directory length");
        assertArrayEquals(expectedCentralData, mergedCentralData, "merged central-directory bytes");
    }
}
