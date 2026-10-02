package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link LZMAUtils} recognizes file names that carry a common LZMA suffix.
 *
 * <p>{@code LZMAUtils} exposes two equivalent entry points: the deprecated
 * {@code isCompressedFilename} and its replacement {@code isCompressedFileName}.
 * Every case below is checked against both so the pair stays in lockstep.</p>
 */
public class LZMAUtilsTest_testIsCompressedFilename {

    /**
     * Asserts that both the deprecated and the current spelling of the method agree
     * on whether {@code fileName} is recognized as an LZMA-compressed file name.
     *
     * @param expected      the recognition result both methods must return.
     * @param fileName      the file name to classify.
     */
    @SuppressWarnings("deprecation")
    private static void assertRecognizedAsLzma(final boolean expected, final String fileName) {
        if (expected) {
            assertTrue(LZMAUtils.isCompressedFilename(fileName));
            assertTrue(LZMAUtils.isCompressedFileName(fileName));
        } else {
            assertFalse(LZMAUtils.isCompressedFilename(fileName));
            assertFalse(LZMAUtils.isCompressedFileName(fileName));
        }
    }

    @Test
    void testIsCompressedFilename() {
        // A bare suffix with no actual file name is not a match.
        assertRecognizedAsLzma(false, "");
        assertRecognizedAsLzma(false, ".lzma");

        // The two recognized LZMA suffixes: ".lzma" and "-lzma".
        assertRecognizedAsLzma(true, "x.lzma");
        assertRecognizedAsLzma(true, "x-lzma");

        // Suffix-like strings that are not actually the LZMA suffix.
        assertRecognizedAsLzma(false, "xxgz");
        assertRecognizedAsLzma(false, "lzmaz");
        assertRecognizedAsLzma(false, "xaz");

        // The suffix must end the name: trailing characters defeat the match.
        assertRecognizedAsLzma(false, "x.lzma ");
        assertRecognizedAsLzma(false, "x.lzma\n");
        assertRecognizedAsLzma(false, "x.lzma.y");
    }
}
