package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies that cloning a {@link StringTokenizer} created from a {@code null}
 * char array produces an independent, fully usable tokenizer.
 */
public class StringTokenizerTest_testCloneNull {

    @Test
    void testCloneNull() {
        // A tokenizer built from a null input has no text and therefore no tokens.
        final StringTokenizer tokenizer = new StringTokenizer((char[]) null);

        // Sanity check: with no input, nextToken() returns null, and resetting
        // the tokenizer keeps that behaviour.
        assertNull(tokenizer.nextToken(), "null input should yield no tokens");
        tokenizer.reset();
        assertNull(tokenizer.nextToken(), "null input should still yield no tokens after reset");

        // Clone the tokenizer; the clone must be independent of the original.
        final StringTokenizer clonedTokenizer = (StringTokenizer) tokenizer.clone();

        // Both the reset original and the clone must behave like an empty tokenizer.
        tokenizer.reset();
        assertNull(tokenizer.nextToken(), "original should still have no tokens");
        assertNull(clonedTokenizer.nextToken(), "clone should also have no tokens");
    }
}
