package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a TSV {@link StringTokenizer} created with no input (or with an
 * empty input string) behaves as a tokenizer that holds no tokens.
 */
public class StringTokenizerTest_testTSVEmpty {

    /**
     * A factory instance must always be a fresh clone, never one of the shared
     * prototype instances returned by the no-argument factory methods.
     */
    private void assertIsFreshClone(final StringTokenizer tokenizer) {
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);
    }

    /**
     * Asserts that the given tokenizer contains no tokens: iteration in either
     * direction yields nothing, the size is zero, and {@code next()} fails.
     */
    private void assertHasNoTokens(final StringTokenizer tokenizer) {
        assertIsFreshClone(tokenizer);
        assertFalse(tokenizer.hasNext(), "an empty tokenizer should have no next token");
        assertFalse(tokenizer.hasPrevious(), "an empty tokenizer should have no previous token");
        assertNull(tokenizer.nextToken(), "nextToken() should return null when empty");
        assertEquals(0, tokenizer.size(), "an empty tokenizer should report size 0");
        assertThrows(NoSuchElementException.class, tokenizer::next,
                "next() should throw when there are no tokens");
    }

    @Test
    void testTSVEmpty() {
        // A TSV tokenizer with no input has no tokens.
        assertHasNoTokens(StringTokenizer.getTSVInstance());
        // A TSV tokenizer initialized with an empty string also has no tokens.
        assertHasNoTokens(StringTokenizer.getTSVInstance(""));
    }
}
