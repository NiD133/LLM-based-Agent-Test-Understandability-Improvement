package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link WordUtils#wrap(String, int, String, boolean, String)} when the
 * {@code wrapOn} regex matches an empty string (zero-length, lookahead) at several
 * positions in the input.
 */
public class WordUtilsTest_testWrapWithMultipleRegexMatchOfLength0 {

    /**
     * The lookahead {@code "(?=d)"} matches at the zero-width position before each
     * 'd'. With a wrap length of 2, the text "abcdefabcdef" is broken at those points,
     * inserting a newline before each "def" segment.
     */
    @Test
    void testWrapWithMultipleRegexMatchOfLength0() {
        final String input = "abcdefabcdef";
        final int wrapLength = 2;
        final String newLine = "\n";
        final boolean wrapLongWords = false;
        final String wrapOnZeroWidthBeforeD = "(?=d)";

        final String wrapped =
                WordUtils.wrap(input, wrapLength, newLine, wrapLongWords, wrapOnZeroWidthBeforeD);

        assertEquals("abc\ndefabc\ndef", wrapped);
    }
}
