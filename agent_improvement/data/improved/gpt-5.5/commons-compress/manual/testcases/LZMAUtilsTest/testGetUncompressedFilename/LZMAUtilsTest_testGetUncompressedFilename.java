package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testGetUncompressedFilename {

    private static final String[][] UNCOMPRESSED_FILENAME_CASES = {
        { "", "" },
        { ".lzma", ".lzma" },
        { "x.lzma", "x" },
        { "x-lzma", "x" },
        { "x.lzma ", "x.lzma " },
        { "x.lzma\n", "x.lzma\n" },
        { "x.lzma.y", "x.lzma.y" }
    };

    @Test
    void testGetUncompressedFilename() {
        for (final String[] testCase : UNCOMPRESSED_FILENAME_CASES) {
            final String compressedFileName = testCase[0];
            final String expectedUncompressedFileName = testCase[1];

            assertDeprecatedApiMapsToUncompressedFileName(compressedFileName, expectedUncompressedFileName);
            assertCurrentApiMapsToUncompressedFileName(compressedFileName, expectedUncompressedFileName);
        }
    }

    @SuppressWarnings("deprecation")
    private static void assertDeprecatedApiMapsToUncompressedFileName(final String compressedFileName,
            final String expectedUncompressedFileName) {
        assertEquals(expectedUncompressedFileName, LZMAUtils.getUncompressedFilename(compressedFileName));
    }

    private static void assertCurrentApiMapsToUncompressedFileName(final String compressedFileName,
            final String expectedUncompressedFileName) {
        assertEquals(expectedUncompressedFileName, LZMAUtils.getUncompressedFileName(compressedFileName));
    }
}
