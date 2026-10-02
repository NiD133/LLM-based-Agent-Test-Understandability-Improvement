package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testGetUncompressedFilename {

    /**
     * An empty string has no LZMA suffix to strip, so it should be returned unchanged.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testGetUncompressedFilename_emptyString_returnsEmptyString() {
        assertEquals("", LZMAUtils.getUncompressedFilename(""),
                "Empty string has no suffix to strip and should be returned as-is");
        assertEquals("", LZMAUtils.getUncompressedFileName(""),
                "Empty string has no suffix to strip and should be returned as-is");
    }

    /**
     * A filename that is only ".lzma" (no base name) should NOT be treated as a
     * compressed file — it is returned unchanged because stripping the suffix
     * would leave an empty base name, which is not a meaningful result.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testGetUncompressedFilename_suffixOnly_returnsUnchanged() {
        assertEquals(".lzma", LZMAUtils.getUncompressedFilename(".lzma"),
                "A lone '.lzma' with no base name should be returned unchanged");
        assertEquals(".lzma", LZMAUtils.getUncompressedFileName(".lzma"),
                "A lone '.lzma' with no base name should be returned unchanged");
    }

    /**
     * A filename ending with the ".lzma" dot-extension suffix should have that
     * suffix stripped, leaving only the base name.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testGetUncompressedFilename_dotLzmaSuffix_stripsExtension() {
        assertEquals("x", LZMAUtils.getUncompressedFilename("x.lzma"),
                "'.lzma' suffix should be stripped from 'x.lzma'");
        assertEquals("x", LZMAUtils.getUncompressedFileName("x.lzma"),
                "'.lzma' suffix should be stripped from 'x.lzma'");
    }

    /**
     * A filename ending with the "-lzma" dash-suffix (an alternative LZMA suffix)
     * should also have that suffix stripped, leaving only the base name.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testGetUncompressedFilename_dashLzmaSuffix_stripsAlternativeSuffix() {
        assertEquals("x", LZMAUtils.getUncompressedFilename("x-lzma"),
                "'-lzma' alternative suffix should be stripped from 'x-lzma'");
        assertEquals("x", LZMAUtils.getUncompressedFileName("x-lzma"),
                "'-lzma' alternative suffix should be stripped from 'x-lzma'");
    }

    /**
     * A trailing space after ".lzma" means the suffix does not match exactly,
     * so the filename should be returned unchanged.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testGetUncompressedFilename_trailingSpaceAfterSuffix_returnsUnchanged() {
        assertEquals("x.lzma ", LZMAUtils.getUncompressedFilename("x.lzma "),
                "Trailing space breaks suffix match; filename should be returned unchanged");
        assertEquals("x.lzma ", LZMAUtils.getUncompressedFileName("x.lzma "),
                "Trailing space breaks suffix match; filename should be returned unchanged");
    }

    /**
     * A trailing newline after ".lzma" means the suffix does not match exactly,
     * so the filename should be returned unchanged.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testGetUncompressedFilename_trailingNewlineAfterSuffix_returnsUnchanged() {
        assertEquals("x.lzma\n", LZMAUtils.getUncompressedFilename("x.lzma\n"),
                "Trailing newline breaks suffix match; filename should be returned unchanged");
        assertEquals("x.lzma\n", LZMAUtils.getUncompressedFileName("x.lzma\n"),
                "Trailing newline breaks suffix match; filename should be returned unchanged");
    }

    /**
     * When ".lzma" appears in the middle of a filename (i.e. another extension
     * follows it), the filename is not a compressed file and should be returned
     * unchanged.
     */
    @SuppressWarnings("deprecation")
    @Test
    void testGetUncompressedFilename_lzmaSuffixFollowedByAnotherExtension_returnsUnchanged() {
        assertEquals("x.lzma.y", LZMAUtils.getUncompressedFilename("x.lzma.y"),
                "'.lzma' embedded before another extension should not be stripped");
        assertEquals("x.lzma.y", LZMAUtils.getUncompressedFileName("x.lzma.y"),
                "'.lzma' embedded before another extension should not be stripped");
    }
}
