package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testIsCompressedFilename {

    private static final String[] COMPRESSED_FILE_NAMES = {
            "x.lzma",
            "x-lzma"
    };

    private static final String[] UNCOMPRESSED_FILE_NAMES = {
            "",
            ".lzma",
            "xxgz",
            "lzmaz",
            "xaz",
            "x.lzma ",
            "x.lzma\n",
            "x.lzma.y"
    };

    @Test
    void testIsCompressedFilename() {
        for (final String fileName : UNCOMPRESSED_FILE_NAMES) {
            assertBothApisReject(fileName);
        }
        for (final String fileName : COMPRESSED_FILE_NAMES) {
            assertBothApisAccept(fileName);
        }
    }

    @SuppressWarnings("deprecation")
    private static void assertBothApisAccept(final String fileName) {
        assertTrue(LZMAUtils.isCompressedFilename(fileName));
        assertTrue(LZMAUtils.isCompressedFileName(fileName));
    }

    @SuppressWarnings("deprecation")
    private static void assertBothApisReject(final String fileName) {
        assertFalse(LZMAUtils.isCompressedFilename(fileName));
        assertFalse(LZMAUtils.isCompressedFileName(fileName));
    }
}
