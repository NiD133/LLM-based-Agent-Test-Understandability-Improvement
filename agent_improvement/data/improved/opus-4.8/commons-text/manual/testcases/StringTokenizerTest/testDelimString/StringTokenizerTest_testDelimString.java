package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testDelimString {

    /**
     * Verifies that a multi-character delimiter ("##") splits the input into the
     * expected tokens, and that the tokenizer is exhausted afterwards.
     */
    @Test
    void testDelimString() {
        final String input = "a##b##c";
        final StringTokenizer tokenizer = new StringTokenizer(input, "##");

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
