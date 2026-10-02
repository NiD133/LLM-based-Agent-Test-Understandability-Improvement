package org.apache.commons.compress.archivers.zip;

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

    private AsiExtraField asiExtraField;

    private UnrecognizedExtraField unrecognizedExtraField;

    private byte[] expectedLocalData;

    private byte[] asiLocalData;

    @BeforeEach
    public void setUp() {
        asiExtraField = new AsiExtraField();
        asiExtraField.setMode(0755);
        asiExtraField.setDirectory(true);

        unrecognizedExtraField = new UnrecognizedExtraField();
        unrecognizedExtraField.setHeaderId(UNRECOGNIZED_HEADER);
        unrecognizedExtraField.setLocalFileDataData(new byte[] { 0 });
        unrecognizedExtraField.setCentralDirectoryData(new byte[] { 0 });

        asiLocalData = asiExtraField.getLocalFileDataData();
        final byte[] unrecognizedLocalData = unrecognizedExtraField.getLocalFileDataData();

        expectedLocalData = new byte[4 + asiLocalData.length + 4 + unrecognizedLocalData.length];
        System.arraycopy(asiExtraField.getHeaderId().getBytes(), 0, expectedLocalData, 0, 2);
        System.arraycopy(asiExtraField.getLocalFileDataLength().getBytes(), 0, expectedLocalData, 2, 2);
        System.arraycopy(asiLocalData, 0, expectedLocalData, 4, asiLocalData.length);
        System.arraycopy(unrecognizedExtraField.getHeaderId().getBytes(), 0, expectedLocalData, 4 + asiLocalData.length, 2);
        System.arraycopy(unrecognizedExtraField.getLocalFileDataLength().getBytes(), 0, expectedLocalData, 4 + asiLocalData.length + 2, 2);
        System.arraycopy(unrecognizedLocalData, 0, expectedLocalData, 4 + asiLocalData.length + 4, unrecognizedLocalData.length);
    }

    /**
     * Test merge methods
     */
    @Test
    void testMerge() {
        final byte[] local = ExtraFieldUtils.mergeLocalFileDataData(new ZipExtraField[] { asiExtraField, unrecognizedExtraField });
        assertBytesEqual("local", expectedLocalData, local);

        final byte[] expectedCentralData = createExpectedCentralData();
        final byte[] central = ExtraFieldUtils.mergeCentralDirectoryData(new ZipExtraField[] { asiExtraField, unrecognizedExtraField });
        assertBytesEqual("central", expectedCentralData, central);
    }

    private byte[] createExpectedCentralData() {
        final byte[] unrecognizedCentralData = unrecognizedExtraField.getCentralDirectoryData();
        final byte[] expectedCentralData = new byte[4 + asiLocalData.length + 4 + unrecognizedCentralData.length];

        System.arraycopy(expectedLocalData, 0, expectedCentralData, 0, 4 + asiLocalData.length + 2);
        System.arraycopy(unrecognizedExtraField.getCentralDirectoryLength().getBytes(), 0, expectedCentralData, 4 + asiLocalData.length + 2, 2);
        System.arraycopy(unrecognizedCentralData, 0, expectedCentralData, 4 + asiLocalData.length + 4, unrecognizedCentralData.length);

        return expectedCentralData;
    }

    private void assertBytesEqual(final String label, final byte[] expected, final byte[] actual) {
        assertEquals(expected.length, actual.length, label + " length");
        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i], label + " byte " + i);
        }
    }
}
