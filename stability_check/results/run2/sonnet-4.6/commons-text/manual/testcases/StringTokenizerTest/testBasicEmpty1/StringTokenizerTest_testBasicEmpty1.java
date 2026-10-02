package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicEmpty1 {

    /**
     * Verifies that when ignoreEmptyTokens is false, consecutive whitespace delimiters
     * produce an empty-string token between them.
     *
     * Input "a  b c" has two spaces between 'a' and 'b', yielding four tokens:
     * "a", "" (empty from the extra space), "b", "c".
     */
    @Test
    void testBasicEmpty1() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setIgnoreEmptyTokens(false);

        assertEquals("a", tokenizer.next(), "first token");
        assertEquals("", tokenizer.next(), "empty token produced by consecutive spaces");
        assertEquals("b", tokenizer.next(), "third token");
        assertEquals("c", tokenizer.next(), "fourth token");
        assertFalse(tokenizer.hasNext(), "no more tokens after the four expected ones");
    }
}
