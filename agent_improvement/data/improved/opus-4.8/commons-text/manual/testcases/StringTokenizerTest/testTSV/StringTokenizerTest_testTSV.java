package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer#getTSVInstance} parses the tab-separated
 * input {@code "A\tb\tc"} into the three tokens {@code "A"}, {@code "b"}, {@code "c"}
 * and that the resulting tokenizer can be navigated both forward and backward.
 */
public class StringTokenizerTest_testTSV {

    /** A tab-separated value containing exactly three tokens: A, b, c. */
    private static final String TSV_SIMPLE_FIXTURE = "A\tb\tc";

    /**
     * Confirms the tokenizer is a fresh, independent instance rather than one of
     * the shared instances returned by the factory methods.
     */
    private void assertNotSharedInstance(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /**
     * Walks a tokenizer that is expected to contain the tokens {@code A}, {@code b},
     * {@code c}, first stepping forward to the end, then stepping back to the start,
     * checking the cursor position ({@code nextIndex}/{@code previousIndex}) at each step.
     */
    private void assertTokensAbc(final StringTokenizer tokenizer) {
        assertNotSharedInstance(tokenizer);

        // Cursor starts before the first token; there is no previous token.
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        // Step forward through all three tokens.
        assertEquals("A", tokenizer.nextToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("b", tokenizer.nextToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("c", tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Past the end: no further token, cursor stays put.
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Step backward through all three tokens.
        assertEquals("c", tokenizer.previousToken());
        assertEquals(2, tokenizer.nextIndex());
        assertEquals("b", tokenizer.previousToken());
        assertEquals(1, tokenizer.nextIndex());
        assertEquals("A", tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());

        // Before the start: no previous token, cursor stays put.
        assertNull(tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());

        assertEquals(3, tokenizer.size());
    }

    @Test
    void testTSV() {
        // The factory accepts the input either as a String or as a char[].
        assertTokensAbc(StringTokenizer.getTSVInstance(TSV_SIMPLE_FIXTURE));
        assertTokensAbc(StringTokenizer.getTSVInstance(TSV_SIMPLE_FIXTURE.toCharArray()));
    }
}
