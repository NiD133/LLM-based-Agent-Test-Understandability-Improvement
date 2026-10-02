package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests that a TSV tokenizer configured with no input (null or empty string)
 * behaves as an empty iterator with zero tokens.
 */
public class StringTokenizerTest_testTSVEmpty {

    /**
     * Verifies that the given tokenizer is a distinct instance from the shared
     * CSV and TSV prototype instances — i.e. it is its own copy, not a shared
     * singleton that could be mutated by other callers.
     */
    private void checkClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /**
     * Asserts that the tokenizer contains no tokens and reports the correct
     * empty-iterator state for every navigation method.
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
     * A TSV tokenizer created with no input and one created with an empty string
     * must both behave as empty iterators.
     */
    @Test
    void testTSVEmpty() {
        testEmpty(StringTokenizer.getTSVInstance());
        testEmpty(StringTokenizer.getTSVInstance(""));
    }
}
