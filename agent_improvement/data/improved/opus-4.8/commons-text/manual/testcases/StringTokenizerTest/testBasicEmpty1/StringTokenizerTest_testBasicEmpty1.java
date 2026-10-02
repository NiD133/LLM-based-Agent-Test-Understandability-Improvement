package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link StringTokenizer} handles an empty token when
 * {@code ignoreEmptyTokens} is disabled.
 */
public class StringTokenizerTest_testBasicEmpty1 {

    /**
     * The input {@code "a  b c"} contains two consecutive spaces between
     * {@code a} and {@code b}. With empty-token ignoring turned off, the gap
     * between those two delimiters must be reported as an empty string token,
     * yielding the sequence: "a", "", "b", "c".
     */
    @Test
    void emptyTokenIsKeptWhenIgnoreEmptyTokensDisabled() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setIgnoreEmptyTokens(false);

        assertEquals("a", tokenizer.next());
        assertEquals("", tokenizer.next(), "empty token between the doubled spaces");
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext(), "all tokens consumed");
    }
}
