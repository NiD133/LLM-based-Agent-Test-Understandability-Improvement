package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringTokenizer#reset(char[])}, which re-points an existing
 * tokenizer at a new character-array input and clears any prior parsing state.
 */
public class StringTokenizerTest_testReset_charArray {

    @Test
    void testReset_charArray() {
        // Start with some unrelated input so we can prove reset() replaces it.
        final StringTokenizer tokenizer = new StringTokenizer("x x x");

        // reset(char[]) should discard the old input and tokenize the new one.
        // The default tokenizer treats whitespace as a delimiter, so "abc"
        // (no whitespace) is parsed as a single token.
        final char[] newInput = {'a', 'b', 'c'};
        tokenizer.reset(newInput);
        assertEquals("abc", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        // Resetting with a null array yields a tokenizer with no tokens.
        tokenizer.reset((char[]) null);
        assertFalse(tokenizer.hasNext());
    }
}
