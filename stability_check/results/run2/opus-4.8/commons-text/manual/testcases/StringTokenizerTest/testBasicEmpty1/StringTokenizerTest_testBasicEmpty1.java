package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies that when empty tokens are not ignored, a run of consecutive
 * delimiters yields an empty-string token between them.
 */
public class StringTokenizerTest_testBasicEmpty1 {

    @Test
    void emptyTokenBetweenConsecutiveDelimitersIsReturned() {
        // The two spaces between "a" and "b" enclose one empty token.
        final StringTokenizer tokenizer = new StringTokenizer("a  b c");
        tokenizer.setIgnoreEmptyTokens(false);

        assertEquals("a", tokenizer.next());
        assertEquals("", tokenizer.next(), "the empty token from the double space");
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext(), "all tokens have been consumed");
    }
}
