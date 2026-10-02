package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testReset {

    /**
     * Verifies that reset() rewinds the tokenizer to the beginning so that all
     * tokens can be iterated a second time with identical results.
     */
    @Test
    void testReset() {
        final StringTokenizer tok = new StringTokenizer("a b c");

        // First pass: consume all three tokens
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext());

        // Reset the tokenizer to the start
        tok.reset();

        // Second pass: same tokens must be available again
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext());
    }
}
