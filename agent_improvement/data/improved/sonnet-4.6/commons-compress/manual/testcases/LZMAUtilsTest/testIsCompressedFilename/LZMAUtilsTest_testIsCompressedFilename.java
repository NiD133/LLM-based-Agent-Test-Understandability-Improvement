package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link LZMAUtils#isCompressedFileName(String)} and its deprecated
 * alias {@link LZMAUtils#isCompressedFilename(String)} correctly identify LZMA
 * file names. The two methods are equivalent; both are exercised here to confirm
 * the deprecation wrapper delegates correctly.
 *
 * <p>LZMA recognises two suffixes: {@code .lzma} and {@code -lzma}.
 * A file name is considered compressed only when the suffix follows at least
 * one non-suffix character (i.e. a bare {@code .lzma} with no base is not
 * recognised).
 */
public class LZMAUtilsTest_testIsCompressedFilename {

    @SuppressWarnings("deprecation")
    @Test
    void testIsCompressedFilename() {

        // --- Empty string: no suffix present ---
        assertFalse(LZMAUtils.isCompressedFilename(""));
        assertFalse(LZMAUtils.isCompressedFileName(""));

        // --- Suffix alone, no base name: not considered a compressed file name ---
        assertFalse(LZMAUtils.isCompressedFilename(".lzma"));
        assertFalse(LZMAUtils.isCompressedFileName(".lzma"));

        // --- Valid LZMA file names: base name + recognised suffix ---
        assertTrue(LZMAUtils.isCompressedFilename("x.lzma"));
        assertTrue(LZMAUtils.isCompressedFileName("x.lzma"));

        assertTrue(LZMAUtils.isCompressedFilename("x-lzma"));
        assertTrue(LZMAUtils.isCompressedFileName("x-lzma"));

        // --- Unrelated extensions: not LZMA-compressed ---
        assertFalse(LZMAUtils.isCompressedFilename("xxgz"));
        assertFalse(LZMAUtils.isCompressedFileName("xxgz"));

        assertFalse(LZMAUtils.isCompressedFilename("lzmaz"));
        assertFalse(LZMAUtils.isCompressedFileName("lzmaz"));

        assertFalse(LZMAUtils.isCompressedFilename("xaz"));
        assertFalse(LZMAUtils.isCompressedFileName("xaz"));

        // --- Edge cases: trailing whitespace, newline, and double extension ---
        // Whitespace after the suffix must not be accepted
        assertFalse(LZMAUtils.isCompressedFilename("x.lzma "));
        assertFalse(LZMAUtils.isCompressedFileName("x.lzma "));

        assertFalse(LZMAUtils.isCompressedFilename("x.lzma\n"));
        assertFalse(LZMAUtils.isCompressedFileName("x.lzma\n"));

        // An additional extension after .lzma means the outermost suffix is not .lzma
        assertFalse(LZMAUtils.isCompressedFilename("x.lzma.y"));
        assertFalse(LZMAUtils.isCompressedFileName("x.lzma.y"));
    }
}
