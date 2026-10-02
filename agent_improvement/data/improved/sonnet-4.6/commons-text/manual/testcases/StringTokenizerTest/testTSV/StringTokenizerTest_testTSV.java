package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testTSV {

    // TSV input: three tab-separated tokens "A", "b", "c"
    private static final String TSV_SIMPLE_FIXTURE = "A\tb\tc";

    /**
     * Verifies that the tokenizer is a fresh instance, not one of the shared prototype singletons.
     * getTSVInstance() / getCSVInstance() return clones of the prototype, so this check
     * guards against accidental mutation of the shared state.
     */
    private void assertNotPrototypeInstance(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /**
     * Exercises bidirectional iteration over a tokenizer that contains exactly three tokens: "A", "b", "c".
     *
     * The test walks forward through all tokens, verifies that a further advance returns null
     * (past-the-end sentinel), then walks backward through all tokens and verifies that a further
     * retreat also returns null (before-the-start sentinel). Index positions are checked at every
     * step to confirm the cursor is tracked correctly.
     */
    private void assertBidirectionalIterationOverAbc(final StringTokenizer tokenizer) {
        assertNotPrototypeInstance(tokenizer);

        // Initial state: cursor is before the first token
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0,  tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        // Forward traversal
        assertEquals("A", tokenizer.nextToken());
        assertEquals(1,   tokenizer.nextIndex());

        assertEquals("b", tokenizer.nextToken());
        assertEquals(2,   tokenizer.nextIndex());

        assertEquals("c", tokenizer.nextToken());
        assertEquals(3,   tokenizer.nextIndex());

        // Past-the-end: nextToken() returns null and the index stays at 3
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Backward traversal
        assertEquals("c", tokenizer.previousToken());
        assertEquals(2,   tokenizer.nextIndex());

        assertEquals("b", tokenizer.previousToken());
        assertEquals(1,   tokenizer.nextIndex());

        assertEquals("A", tokenizer.previousToken());
        assertEquals(0,   tokenizer.nextIndex());

        // Before-the-start: previousToken() returns null, cursor stays at index 0 / previousIndex -1
        assertNull(tokenizer.previousToken());
        assertEquals(0,  tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());

        assertEquals(3, tokenizer.size());
    }

    @Test
    void testTSV() {
        // Verify tokenization from a String and from the equivalent char array
        assertBidirectionalIterationOverAbc(StringTokenizer.getTSVInstance(TSV_SIMPLE_FIXTURE));
        assertBidirectionalIterationOverAbc(StringTokenizer.getTSVInstance(TSV_SIMPLE_FIXTURE.toCharArray()));
    }
}
