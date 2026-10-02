package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies the {@link StringTokenizer#StringTokenizer(String, char)} constructor,
 * which tokenizes a string using a single character as the delimiter.
 */
public class StringTokenizerTest_testConstructor_String_char {

    @Test
    void testConstructor_String_char() {
        // A space is used as the single delimiter character.
        final char delimiter = ' ';

        // Tokenizing "a b" on a space should yield exactly two tokens: "a" and "b".
        StringTokenizer tokenizer = new StringTokenizer("a b", delimiter);

        // The constructor must register the given char as the delimiter matcher,
        // so matching a single space (as char[] or as String) reports a 1-char match.
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(" ", 0, 0, 1));

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        // An empty input string produces no tokens.
        tokenizer = new StringTokenizer("", delimiter);
        assertFalse(tokenizer.hasNext());

        // A null input string is treated as empty and produces no tokens.
        tokenizer = new StringTokenizer((String) null, delimiter);
        assertFalse(tokenizer.hasNext());
    }
}
