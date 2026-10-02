package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@code StringTokenizer(String, char delim, char quote)} constructor,
 * verifying that the supplied delimiter and quote characters are turned into the
 * matchers used during tokenization and that the input is split as expected.
 */
public class StringTokenizerTest_testConstructor_String_char_char {

    private static final char DELIMITER = ' ';

    private static final char QUOTE = '"';

    @Test
    void testConstructor_String_char_char() {
        // The space delimiter and double-quote are stored as matchers that
        // recognize their respective single characters (isMatch returns the
        // matched length, i.e. 1).
        StringTokenizer tokenizer = new StringTokenizer("a b", DELIMITER, QUOTE);
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(" ", 0, 0, 1));
        assertEquals(1, tokenizer.getQuoteMatcher().isMatch("\"".toCharArray(), 0, 0, 1));
        assertEquals(1, tokenizer.getQuoteMatcher().isMatch("\"", 0, 0, 1));

        // "a b" splits on the space into exactly two tokens.
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        // An empty input yields no tokens.
        tokenizer = new StringTokenizer("", DELIMITER, QUOTE);
        assertFalse(tokenizer.hasNext());

        // A null input also yields no tokens.
        tokenizer = new StringTokenizer((String) null, DELIMITER, QUOTE);
        assertFalse(tokenizer.hasNext());
    }
}
