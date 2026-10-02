package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link StringTokenizer#StringTokenizer(char[], char, char)} constructor,
 * which builds a tokenizer from a char array using a single delimiter character and a
 * single quote character.
 */
public class StringTokenizerTest_testConstructor_charArray_char_char {

    /** A match by these single-character matchers spans exactly one character. */
    private static final int SINGLE_CHAR_MATCH_LENGTH = 1;

    /** Offset of the first (and only) character in the probe arrays/strings below. */
    private static final int FIRST_CHAR = 0;

    @Test
    void testConstructor_charArray_char_char() {
        final char delimiter = ' ';
        final char quote = '"';

        // Build the tokenizer from a char array, using space as the delimiter and
        // double-quote as the quote character.
        StringTokenizer tokenizer = new StringTokenizer("a b".toCharArray(), delimiter, quote);

        // The constructor must install a delimiter matcher that matches a single space.
        // isMatch returns the number of matched characters, so 1 confirms a match of length one.
        assertEquals(SINGLE_CHAR_MATCH_LENGTH,
                tokenizer.getDelimiterMatcher().isMatch(" ".toCharArray(), FIRST_CHAR, FIRST_CHAR, 1));
        assertEquals(SINGLE_CHAR_MATCH_LENGTH,
                tokenizer.getDelimiterMatcher().isMatch(" ", FIRST_CHAR, FIRST_CHAR, 1));

        // Likewise, the quote matcher must match a single double-quote character.
        assertEquals(SINGLE_CHAR_MATCH_LENGTH,
                tokenizer.getQuoteMatcher().isMatch("\"".toCharArray(), FIRST_CHAR, FIRST_CHAR, 1));
        assertEquals(SINGLE_CHAR_MATCH_LENGTH,
                tokenizer.getQuoteMatcher().isMatch("\"", FIRST_CHAR, FIRST_CHAR, 1));

        // "a b" split on space yields exactly the two tokens "a" and "b".
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        // An empty char array produces no tokens.
        tokenizer = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY, delimiter, quote);
        assertFalse(tokenizer.hasNext());

        // A null char array also produces no tokens.
        tokenizer = new StringTokenizer((char[]) null, delimiter, quote);
        assertFalse(tokenizer.hasNext());
    }
}
