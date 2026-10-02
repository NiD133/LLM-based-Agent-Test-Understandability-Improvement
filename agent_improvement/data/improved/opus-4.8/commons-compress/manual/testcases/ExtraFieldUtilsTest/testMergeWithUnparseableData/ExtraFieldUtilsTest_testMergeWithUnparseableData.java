package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link ExtraFieldUtils#mergeLocalFileDataData} and
 * {@link ExtraFieldUtils#mergeCentralDirectoryData} handle a trailing
 * {@link UnparseableExtraFieldData} holder correctly.
 *
 * <p>
 * Each serialized extra field is laid out as:
 * {@code [header-id: 2 bytes][data-length: 2 bytes][payload: data-length bytes]}.
 * The 4-byte {@code header-id + data-length} prefix is referred to as the field header below.
 * </p>
 */
public class ExtraFieldUtilsTest_testMergeWithUnparseableData implements UnixStat {

    /** Size in bytes of a serialized extra field header (2-byte header id + 2-byte length). */
    private static final int FIELD_HEADER_LENGTH = 4;

    /**
     * Header-ID of a ZipExtraField not supported by Commons Compress.
     *
     * <p>
     * Used to be ZipShort(1) but this is the ID of the Zip64 extra field.
     * </p>
     */
    static final ZipShort UNRECOGNIZED_HEADER = new ZipShort(0x5555);

    /** A recognized field (Asi) that is serialized normally. */
    private AsiExtraField a;

    /** Local-file-data payload of {@link #a}, cached for convenience. */
    private byte[] aLocal;

    /**
     * The expected fully serialized local file data, built from {@link #a} followed by an
     * unrecognized dummy field. The unparseable-data test reuses this as its expected prefix.
     */
    private byte[] expectedLocalData;

    @BeforeEach
    public void setUp() {
        // A recognized, well-formed extra field.
        a = new AsiExtraField();
        a.setMode(0755);
        a.setDirectory(true);
        aLocal = a.getLocalFileDataData();

        // An unrecognized field appended after "a" to form the expected byte layout.
        final UnrecognizedExtraField dummy = new UnrecognizedExtraField();
        dummy.setHeaderId(UNRECOGNIZED_HEADER);
        dummy.setLocalFileDataData(new byte[] { 0 });
        dummy.setCentralDirectoryData(new byte[] { 0 });
        final byte[] dummyLocal = dummy.getLocalFileDataData();

        // Serialize [a-header][a-payload][dummy-header][dummy-payload] into expectedLocalData.
        expectedLocalData = new byte[FIELD_HEADER_LENGTH + aLocal.length + FIELD_HEADER_LENGTH + dummyLocal.length];

        final int aHeaderStart = 0;
        final int aPayloadStart = aHeaderStart + FIELD_HEADER_LENGTH;
        final int dummyHeaderStart = aPayloadStart + aLocal.length;
        final int dummyPayloadStart = dummyHeaderStart + FIELD_HEADER_LENGTH;

        System.arraycopy(a.getHeaderId().getBytes(), 0, expectedLocalData, aHeaderStart, 2);
        System.arraycopy(a.getLocalFileDataLength().getBytes(), 0, expectedLocalData, aHeaderStart + 2, 2);
        System.arraycopy(aLocal, 0, expectedLocalData, aPayloadStart, aLocal.length);
        System.arraycopy(dummy.getHeaderId().getBytes(), 0, expectedLocalData, dummyHeaderStart, 2);
        System.arraycopy(dummy.getLocalFileDataLength().getBytes(), 0, expectedLocalData, dummyHeaderStart + 2, 2);
        System.arraycopy(dummyLocal, 0, expectedLocalData, dummyPayloadStart, dummyLocal.length);
    }

    @Test
    void testMergeWithUnparseableData() throws Exception {
        // Build an UnparseableExtraFieldData whose raw bytes are the unrecognized header
        // followed by a claimed length of 1 and a single payload byte.
        final ZipExtraField unparseable = new UnparseableExtraFieldData();
        final byte[] headerBytes = UNRECOGNIZED_HEADER.getBytes();
        final byte[] rawUnparseable = new byte[] { headerBytes[0], headerBytes[1], 1, 0 };
        unparseable.parseFromLocalFileData(rawUnparseable, 0, FIELD_HEADER_LENGTH);

        // Merging local file data: the unparseable holder contributes its raw bytes verbatim
        // (no extra 4-byte header), so the result is one byte shorter than expectedLocalData.
        final byte[] mergedLocal = ExtraFieldUtils.mergeLocalFileDataData(new ZipExtraField[] { a, unparseable });
        assertEquals(expectedLocalData.length - 1, mergedLocal.length, "local length");
        for (int i = 0; i < mergedLocal.length; i++) {
            assertEquals(expectedLocalData[i], mergedLocal[i], "local byte " + i);
        }

        // Merging central directory data: the holder contributes its central directory bytes,
        // replacing the dummy payload portion of the expected layout.
        final byte[] unparseableCentral = unparseable.getCentralDirectoryData();
        final int unparseableStart = FIELD_HEADER_LENGTH + aLocal.length;
        final byte[] expectedCentralData = new byte[unparseableStart + unparseableCentral.length];
        System.arraycopy(expectedLocalData, 0, expectedCentralData, 0, unparseableStart + 2);
        System.arraycopy(unparseableCentral, 0, expectedCentralData, unparseableStart, unparseableCentral.length);

        final byte[] mergedCentral = ExtraFieldUtils.mergeCentralDirectoryData(new ZipExtraField[] { a, unparseable });
        assertEquals(expectedCentralData.length, mergedCentral.length, "central length");
        for (int i = 0; i < mergedCentral.length; i++) {
            assertEquals(expectedCentralData[i], mergedCentral[i], "central byte " + i);
        }
    }
}
