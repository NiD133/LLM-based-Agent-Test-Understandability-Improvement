package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic1 {

    @Test
    void testBasic1() {
        // Whitespace is the default delimiter; multiple consecutive spaces are treated as one.
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
