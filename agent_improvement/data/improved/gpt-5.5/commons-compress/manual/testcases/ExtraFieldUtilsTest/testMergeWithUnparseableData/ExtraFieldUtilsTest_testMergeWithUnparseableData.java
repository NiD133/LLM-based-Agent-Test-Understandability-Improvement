package org.apache.commons.compress.archivers.zip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExtraFieldUtilsTest_testMergeWithUnparseableData implements UnixStat {

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

    private AsiExtraField asiField;

    private UnrecognizedExtraField unrecognizedField;

    private byte[] expectedLocalData;

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

        expectedLocalData = new byte[4 + asiLocalData.length + 4 + unrecognizedLocalData.length];
        System.arraycopy(asiField.getHeaderId().getBytes(), 0, expectedLocalData, 0, 2);
        System.arraycopy(asiField.getLocalFileDataLength().getBytes(), 0, expectedLocalData, 2, 2);
        System.arraycopy(asiLocalData, 0, expectedLocalData, 4, asiLocalData.length);
        System.arraycopy(unrecognizedField.getHeaderId().getBytes(), 0, expectedLocalData, 4 + asiLocalData.length, 2);
        System.arraycopy(unrecognizedField.getLocalFileDataLength().getBytes(), 0, expectedLocalData, 4 + asiLocalData.length + 2, 2);
        System.arraycopy(unrecognizedLocalData, 0, expectedLocalData, 4 + asiLocalData.length + 4, unrecognizedLocalData.length);
    }

    @Test
    void testMergeWithUnparseableData() throws Exception {
        final ZipExtraField unparseableField = new UnparseableExtraFieldData();
        final byte[] unrecognizedHeaderBytes = UNRECOGNIZED_HEADER.getBytes();
        unparseableField.parseFromLocalFileData(new byte[] { unrecognizedHeaderBytes[0], unrecognizedHeaderBytes[1], 1, 0 }, 0, 4);

        final byte[] local = ExtraFieldUtils.mergeLocalFileDataData(new ZipExtraField[] { asiField, unparseableField });
        assertEquals(expectedLocalData.length - 1, local.length, "local length");
        assertByteByByteEquals(expectedLocalData, local, "local byte ");

        final byte[] unparseableCentralData = unparseableField.getCentralDirectoryData();
        final byte[] expectedCentralData = new byte[4 + asiLocalData.length + unparseableCentralData.length];
        System.arraycopy(expectedLocalData, 0, expectedCentralData, 0, 4 + asiLocalData.length + 2);
        System.arraycopy(unparseableCentralData, 0, expectedCentralData, 4 + asiLocalData.length, unparseableCentralData.length);

        final byte[] central = ExtraFieldUtils.mergeCentralDirectoryData(new ZipExtraField[] { asiField, unparseableField });
        assertEquals(expectedCentralData.length, central.length, "central length");
        assertByteByByteEquals(expectedCentralData, central, "central byte ");
    }

    private static void assertByteByByteEquals(final byte[] expected, final byte[] actual, final String messagePrefix) {
        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i], messagePrefix + i);
        }
    }
}
