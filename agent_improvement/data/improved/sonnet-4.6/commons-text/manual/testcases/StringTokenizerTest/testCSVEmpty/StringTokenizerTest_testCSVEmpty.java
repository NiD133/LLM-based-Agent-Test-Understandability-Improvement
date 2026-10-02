package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testCSVEmpty {

    /**
     * Verifies that a CSV tokenizer with no input produces no tokens and
     * behaves correctly when navigation methods are called on an empty sequence.
     */
    private void assertTokenizerIsEmpty(final StringTokenizer tokenizer) {
        // The tokenizer must be a distinct instance, not the shared CSV/TSV prototype
        assertNotSame(StringTokenizer.getCSVInstance(), tokenizer);
        assertNotSame(StringTokenizer.getTSVInstance(), tokenizer);

        // An empty tokenizer has no forward or backward tokens
        assertFalse(tokenizer.hasNext());
        assertFalse(tokenizer.hasPrevious());

        // nextToken() returns null (not an exception) when there are no more tokens
        assertNull(tokenizer.nextToken());

        // The total token count must be zero for an empty input
        assertEquals(0, tokenizer.size());

        // next() (iterator style) throws NoSuchElementException when exhausted
        assertThrows(NoSuchElementException.class, tokenizer::next);
    }

    @Test
    void testCSVEmpty() {
        // Case 1: CSV instance created with no argument (null input)
        assertTokenizerIsEmpty(StringTokenizer.getCSVInstance());

        // Case 2: CSV instance created with an empty string
        assertTokenizerIsEmpty(StringTokenizer.getCSVInstance(""));
    }
}
