package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testReset_charArray {

    /**
     * Verifies that reset(char[]) replaces the tokenizer's input so subsequent
     * calls iterate over the new content, and that reset(null) leaves no tokens.
     */
    @Test
    void testReset_charArray() {
        // Start with a whitespace-delimited string
        final StringTokenizer tok = new StringTokenizer("x x x");

        // Reset with a char array containing no delimiters — yields a single token "abc"
        final char[] singleTokenArray = { 'a', 'b', 'c' };
        tok.reset(singleTokenArray);
        assertEquals("abc", tok.next(), "Resetting with a char array should replace the tokenized content");
        assertFalse(tok.hasNext(), "No further tokens should remain after consuming the only token");

        // Reset with null — produces an empty tokenizer
        tok.reset((char[]) null);
        assertFalse(tok.hasNext(), "Resetting with null should result in no tokens");
    }
}
