package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicEmpty1 {

    @Test
    void testBasicEmpty1() {
        final String inputWithAdjacentDelimiters = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(inputWithAdjacentDelimiters);
        tokenizer.setIgnoreEmptyTokens(false);

        assertEquals("a", tokenizer.next());
        assertEquals("", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
