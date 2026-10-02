package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicEmpty1 {

    @Test
    void testBasicEmpty1() {
        // Double space between "a" and "b" produces an empty token when ignoreEmptyTokens is false.
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setIgnoreEmptyTokens(false);

        assertEquals("a", tokenizer.next());
        assertEquals("", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
