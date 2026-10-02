package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link WordUtils#wrap(String, int, String, boolean, String)} when the
 * {@code wrapOn} regular expression is a zero-width match (it matches a position
 * rather than consuming any characters).
 */
public class WordUtilsTest_testWrapWithRegexMatchOfLength0 {

    @Test
    void testWrapWithRegexMatchOfLength0() {
        // Input to wrap, broken into lines no longer than 2 characters.
        final String textToWrap = "abcdef";
        final int wrapLength = 2;
        final String newLineSeparator = "\n";
        final boolean wrapLongWords = false;
        // Zero-width look-ahead: matches the empty position immediately before 'd'
        // without consuming the 'd', so the break is inserted there.
        final String wrapOnRegex = "(?=d)";

        final String wrapped =
                WordUtils.wrap(textToWrap, wrapLength, newLineSeparator, wrapLongWords, wrapOnRegex);

        // The line break lands right before 'd': "abc" + "\n" + "def".
        assertEquals("abc\ndef", wrapped);
    }
}
