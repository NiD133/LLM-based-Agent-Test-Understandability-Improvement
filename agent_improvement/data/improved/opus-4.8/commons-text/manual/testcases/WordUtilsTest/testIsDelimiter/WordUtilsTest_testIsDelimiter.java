package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#isDelimiter(char, char[])}.
 *
 * <p>The method answers a single question: "is the given character one of the
 * supplied delimiters?". The contract has two cases:</p>
 * <ul>
 *   <li>when the delimiter array is {@code null}, any whitespace character is a delimiter;</li>
 *   <li>otherwise, a character is a delimiter only if it is present in the array.</li>
 * </ul>
 */
public class WordUtilsTest_testIsDelimiter {

    @Test
    void testIsDelimiter() {
        // null delimiters -> only whitespace characters count as delimiters
        assertFalse(WordUtils.isDelimiter('.', null), "'.' is not whitespace");
        assertTrue(WordUtils.isDelimiter(' ', null), "' ' is whitespace");

        // explicit delimiters -> a character is a delimiter only if it is in the array
        assertFalse(WordUtils.isDelimiter(' ', new char[] {'.'}), "' ' is not among {'.'}");
        assertTrue(WordUtils.isDelimiter('.', new char[] {'.'}), "'.' is among {'.'}");

        // larger delimiter sets behave the same way
        assertFalse(WordUtils.isDelimiter(' ', new char[] {'.', '_', 'a'}), "' ' is not among {'.','_','a'}");
        assertTrue(WordUtils.isDelimiter('.', new char[] {'.', '_', 'a', '.'}), "'.' is among {'.','_','a','.'}");
    }
}
