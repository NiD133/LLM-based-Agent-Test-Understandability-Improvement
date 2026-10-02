package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicEmpty1 {

    /**
     * Verifies that consecutive delimiters produce empty tokens when ignoreEmptyTokens is false.
     * Input "a  b c" has two spaces between "a" and "b", so the tokenizer yields four tokens:
     * "a", "" (the empty token between the two spaces), "b", and "c".
     */
    @Test
    void testBasicEmpty1() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setIgnoreEmptyTokens(false);

        assertEquals("a", tokenizer.next(), "first token");
        assertEquals("", tokenizer.next(), "empty token from consecutive delimiters");
        assertEquals("b", tokenizer.next(), "third token");
        assertEquals("c", tokenizer.next(), "fourth token");
        assertFalse(tokenizer.hasNext(), "no more tokens after the last one");
    }
}
