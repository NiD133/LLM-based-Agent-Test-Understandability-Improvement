package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testReset_charArray {

    private static final String ORIGINAL_INPUT = "x x x";
    private static final char[] RESET_INPUT = { 'a', 'b', 'c' };

    @Test
    void testReset_charArray() {
        final StringTokenizer tokenizer = new StringTokenizer(ORIGINAL_INPUT);

        tokenizer.reset(RESET_INPUT);
        assertEquals("abc", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        tokenizer.reset((char[]) null);
        assertFalse(tokenizer.hasNext());
    }
}
