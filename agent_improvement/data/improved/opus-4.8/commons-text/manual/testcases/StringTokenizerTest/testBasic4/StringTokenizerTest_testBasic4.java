package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic4 {

    /**
     * Verifies that the default tokenizer splits on whitespace and preserves a
     * quoted token verbatim (the surrounding quote characters are kept).
     */
    @Test
    void testBasic4() {
        final String input = "a \"b\" c";
        final StringTokenizer tokenizer = new StringTokenizer(input);

        assertEquals("a", tokenizer.next());
        assertEquals("\"b\"", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
