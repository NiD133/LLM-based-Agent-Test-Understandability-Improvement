package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#isDelimiter(int, char[])}, the code-point based overload.
 *
 * <p>The method answers a single question: "is the given code point a delimiter?"
 * Its behaviour depends on the {@code delimiters} argument:</p>
 * <ul>
 *   <li>when {@code delimiters} is {@code null}, any Unicode whitespace code point is a delimiter;</li>
 *   <li>otherwise, only the code points contained in the {@code delimiters} array are delimiters.</li>
 * </ul>
 */
public class WordUtilsTest_testIsDelimiterCodePoint {

    /** Code point of a regular space character, used as a sample whitespace character. */
    private static final int SPACE = ' ';

    /** Code point of a dot, used as a sample non-whitespace character. */
    private static final int DOT = '.';

    @Test
    void testIsDelimiterCodePoint() {
        // With a null delimiter set, whitespace is the delimiter and nothing else is.
        assertFalse(WordUtils.isDelimiter(DOT, null),
                "'.' is not whitespace, so it must not be a delimiter when delimiters is null");
        assertTrue(WordUtils.isDelimiter(SPACE, null),
                "' ' is whitespace, so it must be a delimiter when delimiters is null");

        // With an explicit delimiter set, only the listed characters are delimiters.
        final char[] dotDelimiter = { '.' };
        assertFalse(WordUtils.isDelimiter(SPACE, dotDelimiter),
                "' ' is not in the delimiter set {'.'}, so it must not be a delimiter");
        assertTrue(WordUtils.isDelimiter(DOT, dotDelimiter),
                "'.' is in the delimiter set {'.'}, so it must be a delimiter");

        // The same rule holds for a larger delimiter set: membership is all that matters.
        final char[] multipleDelimiters = { '.', '_', 'a' };
        assertFalse(WordUtils.isDelimiter(SPACE, multipleDelimiters),
                "' ' is not in the delimiter set {'.', '_', 'a'}, so it must not be a delimiter");

        final char[] multipleDelimitersWithDuplicate = { '.', '_', 'a', '.' };
        assertTrue(WordUtils.isDelimiter(DOT, multipleDelimitersWithDuplicate),
                "'.' is in the delimiter set {'.', '_', 'a', '.'}, so it must be a delimiter");
    }
}
