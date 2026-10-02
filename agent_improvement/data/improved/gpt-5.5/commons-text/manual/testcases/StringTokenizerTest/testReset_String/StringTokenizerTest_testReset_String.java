package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testReset_String {

    @Test
    void testReset_String() {
        final StringTokenizer tokenizer = new StringTokenizer("x x x");

        tokenizer.reset("d e");
        assertEquals("d", tokenizer.next());
        assertEquals("e", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        tokenizer.reset((String) null);
        assertFalse(tokenizer.hasNext());
    }
}
