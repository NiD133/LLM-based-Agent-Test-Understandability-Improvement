package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a CSV {@link StringTokenizer} created from empty input
 * behaves as a well-formed, fully consumed tokenizer holding no tokens.
 */
public class StringTokenizerTest_testCSVEmpty {

    /**
     * Asserts that the tokenizer is a fresh instance, distinct from the
     * shared CSV and TSV singletons returned by the factory methods.
     */
    private void assertIsFreshInstance(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /**
     * Asserts that the tokenizer contains no tokens: it cannot iterate in
     * either direction, yields a null token, reports size zero, and throws
     * when {@link StringTokenizer#next()} is called.
     */
    private void assertHasNoTokens(final StringTokenizer tokenizer) {
        assertIsFreshInstance(tokenizer);
        assertFalse(tokenizer.hasNext());
        assertFalse(tokenizer.hasPrevious());
        assertNull(tokenizer.nextToken());
        assertEquals(0, tokenizer.size());
        assertThrows(NoSuchElementException.class, tokenizer::next);
    }

    @Test
    void testCSVEmpty() {
        // A CSV tokenizer with no input string has no tokens.
        assertHasNoTokens(StringTokenizer.getCSVInstance());
        // A CSV tokenizer over an explicitly empty string behaves identically.
        assertHasNoTokens(StringTokenizer.getCSVInstance(""));
    }
}
