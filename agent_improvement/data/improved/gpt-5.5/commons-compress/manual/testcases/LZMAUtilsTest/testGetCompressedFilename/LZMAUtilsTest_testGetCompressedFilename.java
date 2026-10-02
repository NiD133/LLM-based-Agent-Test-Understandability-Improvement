package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testGetCompressedFilename {

    @Test
    void testGetCompressedFilename() {
        assertCompressedFileName("", ".lzma");
        assertCompressedFileName("x", "x.lzma");
        assertCompressedFileName("x.wmf ", "x.wmf .lzma");
        assertCompressedFileName("x.wmf\n", "x.wmf\n.lzma");
        assertCompressedFileName("x.wmf.y", "x.wmf.y.lzma");
    }

    @SuppressWarnings("deprecation")
    private void assertCompressedFileName(final String fileName, final String expectedCompressedFileName) {
        assertEquals(expectedCompressedFileName, LZMAUtils.getCompressedFilename(fileName));
        assertEquals(expectedCompressedFileName, LZMAUtils.getCompressedFileName(fileName));
    }
}
