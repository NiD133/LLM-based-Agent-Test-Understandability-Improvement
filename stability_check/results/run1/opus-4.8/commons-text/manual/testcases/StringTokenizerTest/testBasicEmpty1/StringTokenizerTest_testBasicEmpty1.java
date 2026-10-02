package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies that when empty tokens are NOT ignored, the tokenizer preserves the
 * empty field produced by consecutive delimiters.
 */
public class StringTokenizerTest_testBasicEmpty1 {

    @Test
    void testBasicEmpty1() {
        // Two spaces between "a" and "b" yield an empty token in between,
        // but only because empty tokens are explicitly kept below.
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setIgnoreEmptyTokens(false);

        assertEquals("a", tokenizer.next());
        assertEquals("", tokenizer.next(), "the double space should surface an empty token");
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext(), "no tokens should remain after the last field");
    }
}
