package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringTokenizer#getCSVInstance(String)} and
 * {@link StringTokenizer#getCSVInstance(char[])} correctly parse the simple,
 * comma-separated input {@code "A,b,c"} into the three tokens {@code "A"},
 * {@code "b"} and {@code "c"}, and that the tokenizer can be iterated both
 * forwards and backwards.
 */
public class StringTokenizerTest_testCSVSimple {

    /** Simple comma-separated input that should yield the tokens "A", "b", "c". */
    private static final String CSV_SIMPLE_INPUT = "A,b,c";

    /**
     * Verifies that a freshly created tokenizer is a distinct, independent
     * instance rather than one of the shared CSV/TSV prototypes.
     */
    private void assertIsFreshInstance(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /**
     * Drives a tokenizer that is expected to produce exactly the tokens
     * "A", "b", "c", checking the values and iteration indices while walking
     * forwards to the end and then back to the start.
     */
    private void assertTokensAreAbc(final StringTokenizer tokenizer) {
        assertIsFreshInstance(tokenizer);

        // Before reading anything: positioned before the first token.
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        // Walk forwards through all three tokens.
        assertEquals("A", tokenizer.nextToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("b", tokenizer.nextToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("c", tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Past the last token: no more tokens, index stays at the end.
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Walk backwards through all three tokens.
        assertEquals("c", tokenizer.previousToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("b", tokenizer.previousToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("A", tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());

        // Before the first token again: no previous token available.
        assertNull(tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());

        assertEquals(3, tokenizer.size());
    }

    @Test
    void testCSVSimple() {
        // The String and char[] factory methods must behave identically.
        assertTokensAreAbc(StringTokenizer.getCSVInstance(CSV_SIMPLE_INPUT));
        assertTokensAreAbc(StringTokenizer.getCSVInstance(CSV_SIMPLE_INPUT.toCharArray()));
    }
}
