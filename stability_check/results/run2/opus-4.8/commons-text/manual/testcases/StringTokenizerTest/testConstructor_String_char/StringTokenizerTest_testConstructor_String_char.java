package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies the {@link StringTokenizer#StringTokenizer(String, char)} constructor,
 * which builds a tokenizer that splits the input on a single delimiter character.
 */
public class StringTokenizerTest_testConstructor_String_char {

    /** The matcher's {@code isMatch} returns the number of matched characters (1 for a single-char match). */
    private static final int SINGLE_CHAR_MATCH = 1;

    private static final char SPACE_DELIMITER = ' ';

    @Test
    void testConstructor_String_char() {
        // A non-empty input is split into tokens on the space delimiter.
        StringTokenizer tokenizer = new StringTokenizer("a b", SPACE_DELIMITER);

        // The constructor installs a delimiter matcher for the space character,
        // verified against both the char[] and String overloads of isMatch.
        assertEquals(SINGLE_CHAR_MATCH,
                tokenizer.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(SINGLE_CHAR_MATCH,
                tokenizer.getDelimiterMatcher().isMatch(" ", 0, 0, 1));

        // "a b" yields exactly the tokens "a" and "b", then is exhausted.
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        // An empty input string produces no tokens.
        tokenizer = new StringTokenizer("", SPACE_DELIMITER);
        assertFalse(tokenizer.hasNext());

        // A null input string also produces no tokens.
        tokenizer = new StringTokenizer((String) null, SPACE_DELIMITER);
        assertFalse(tokenizer.hasNext());
    }
}
