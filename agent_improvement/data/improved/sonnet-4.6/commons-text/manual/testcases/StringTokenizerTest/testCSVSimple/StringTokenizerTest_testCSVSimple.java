package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link StringTokenizer#getCSVInstance(String)} and its char-array overload
 * correctly tokenize a simple three-token CSV string ("A,b,c") and support
 * bidirectional iteration via the {@link java.util.ListIterator} API.
 */
public class StringTokenizerTest_testCSVSimple {

    private static final String CSV_SIMPLE_FIXTURE = "A,b,c";

    // -----------------------------------------------------------------------
    // Entry point
    // -----------------------------------------------------------------------

    @Test
    void testCSVSimple() {
        testCSV(CSV_SIMPLE_FIXTURE);
    }

    // -----------------------------------------------------------------------
    // Helpers – exercise both String and char[] factory overloads
    // -----------------------------------------------------------------------

    private void testCSV(final String data) {
        assertBidirectionalIterationOfAbc(StringTokenizer.getCSVInstance(data));
        assertBidirectionalIterationOfAbc(StringTokenizer.getCSVInstance(data.toCharArray()));
    }

    // -----------------------------------------------------------------------
    // Assertion helpers
    // -----------------------------------------------------------------------

    /**
     * Verifies that {@code tokenizer} is a fresh instance, not one of the shared
     * CSV/TSV prototype singletons returned by the factory methods.
     */
    private void checkClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /**
     * Asserts that a tokenizer that was created from empty / null input reports
     * itself as having no tokens and throws on {@code next()}.
     */
    void testEmpty(final StringTokenizer tokenizer) {
        checkClone(tokenizer);
        assertFalse(tokenizer.hasNext());
        assertFalse(tokenizer.hasPrevious());
        assertNull(tokenizer.nextToken());
        assertEquals(0, tokenizer.size());
        assertThrows(NoSuchElementException.class, tokenizer::next);
    }

    /**
     * Fully exercises the bidirectional {@link java.util.ListIterator} contract for a
     * tokenizer whose tokens are exactly ["A", "b", "c"].
     *
     * <p>The test walks forward through all three tokens, verifying each token value
     * and the cursor index after every step. It then walks backward through the same
     * tokens, again verifying values and index positions, to confirm that
     * {@code previousToken()} mirrors {@code nextToken()} symmetrically.
     */
    void assertBidirectionalIterationOfAbc(final StringTokenizer tokenizer) {
        checkClone(tokenizer);

        // Initial state: cursor is before the first token
        assertEquals(-1, tokenizer.previousIndex());
        assertEquals(0, tokenizer.nextIndex());
        assertNull(tokenizer.previousToken());

        // Forward pass: consume all three tokens one by one
        assertEquals("A", tokenizer.nextToken());
        assertEquals(1, tokenizer.nextIndex());

        assertEquals("b", tokenizer.nextToken());
        assertEquals(2, tokenizer.nextIndex());

        assertEquals("c", tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Past the end: nextToken() returns null and the cursor stays at 3
        assertNull(tokenizer.nextToken());
        assertEquals(3, tokenizer.nextIndex());

        // Backward pass: walk back through all three tokens in reverse order
        assertEquals("c", tokenizer.previousToken());
        assertEquals(2, tokenizer.nextIndex());

        assertEquals("b", tokenizer.previousToken());
        assertEquals(1, tokenizer.nextIndex());

        assertEquals("A", tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());

        // Before the beginning: previousToken() returns null; cursor stays at 0 / -1
        assertNull(tokenizer.previousToken());
        assertEquals(0, tokenizer.nextIndex());
        assertEquals(-1, tokenizer.previousIndex());

        // Total token count is unchanged throughout iteration
        assertEquals(3, tokenizer.size());
    }
}
