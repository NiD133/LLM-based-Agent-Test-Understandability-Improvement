package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testReset_String {

    @Test
    void testReset_String() {
        // Create tokenizer with initial content, then replace it via reset(String)
        final StringTokenizer tok = new StringTokenizer("x x x");
        tok.reset("d e");
        assertEquals("d", tok.next());
        assertEquals("e", tok.next());
        assertFalse(tok.hasNext());

        // reset(null) clears the tokenizer so it yields no tokens
        tok.reset((String) null);
        assertFalse(tok.hasNext());
    }
}
