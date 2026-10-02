package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringTokenizer#reset()}.
 */
public class StringTokenizerTest_testReset {

    /** Input whose space-separated tokens are "a", "b" and "c". */
    private static final String SPACE_SEPARATED_INPUT = "a b c";

    /**
     * Consumes the tokenizer from its current position and asserts that it
     * yields the tokens "a", "b", "c" and is then exhausted.
     */
    private void assertYieldsAbcThenExhausted(final StringTokenizer tokenizer) {
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }

    @Test
    void testReset() {
        final StringTokenizer tokenizer = new StringTokenizer(SPACE_SEPARATED_INPUT);

        // Iterate through every token once.
        assertYieldsAbcThenExhausted(tokenizer);

        // reset() rewinds the tokenizer so the same input can be iterated again.
        tokenizer.reset();
        assertYieldsAbcThenExhausted(tokenizer);
    }
}
