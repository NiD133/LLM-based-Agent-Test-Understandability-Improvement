package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link StringTokenizer#clone()} produces an independent copy whose
 * parsed content is decoupled from the original tokenizer and from the backing
 * character array, even after the original is {@link StringTokenizer#reset(char[])}.
 */
public class StringTokenizerTest_testCloneReset {

    @Test
    void testCloneReset() {
        final char[] sharedInput = {'a'};
        final StringTokenizer tokenizer = new StringTokenizer(sharedInput);

        // Sanity check: the tokenizer reads 'a', and resetting it with the same
        // (still unchanged) array yields 'a' again.
        assertEquals("a", tokenizer.nextToken());
        tokenizer.reset(sharedInput);
        assertEquals("a", tokenizer.nextToken());

        // Clone the tokenizer while it still holds "a".
        final StringTokenizer clonedTokenizer = (StringTokenizer) tokenizer.clone();

        // Mutate the shared array and re-point the original tokenizer at it.
        sharedInput[0] = 'b';
        tokenizer.reset(sharedInput);

        // The original now sees 'b', while the clone keeps its own snapshot of 'a'.
        assertEquals("b", tokenizer.nextToken());
        assertEquals("a", clonedTokenizer.nextToken());
    }
}
