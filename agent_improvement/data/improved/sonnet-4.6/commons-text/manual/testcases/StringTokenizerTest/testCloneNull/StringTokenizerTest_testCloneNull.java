package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testCloneNull {

    /**
     * Verifies that a StringTokenizer constructed with a null char array can be cloned,
     * and that both the original and the clone return null from nextToken() independently.
     */
    @Test
    void testCloneNull() {
        // A tokenizer backed by null input should produce no tokens
        final StringTokenizer original = new StringTokenizer((char[]) null);
        assertNull(original.nextToken(), "nextToken() should be null before reset when input is null");

        original.reset();
        assertNull(original.nextToken(), "nextToken() should still be null after reset when input is null");

        // Clone should independently reflect the same null-input behaviour
        final StringTokenizer clone = (StringTokenizer) original.clone();

        original.reset();
        assertNull(original.nextToken(), "original: nextToken() should be null after reset post-clone");
        assertNull(clone.nextToken(), "clone: nextToken() should be null because input was null");
    }
}
