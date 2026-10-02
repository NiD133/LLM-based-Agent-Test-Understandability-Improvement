package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringTokenizer#reset(String)}.
 *
 * <p>{@code reset(String)} discards any previous input and iteration state, then loads a new
 * string to tokenize. This lets a single tokenizer instance be reused for several inputs.</p>
 */
public class StringTokenizerTest_testReset_String {

    @Test
    void testReset_String() {
        // Start with one input, then reset to a brand-new input before reading anything.
        final StringTokenizer tokenizer = new StringTokenizer("x x x");
        tokenizer.reset("d e");

        // The tokenizer now parses the input given to reset(), split on whitespace.
        assertEquals("d", tokenizer.next());
        assertEquals("e", tokenizer.next());
        assertFalse(tokenizer.hasNext(), "no tokens should remain after the last one");

        // Resetting with null clears the input, leaving nothing to iterate over.
        tokenizer.reset((String) null);
        assertFalse(tokenizer.hasNext(), "a null reset should leave no tokens");
    }
}
