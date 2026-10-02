package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic1 {

    /**
     * Verifies the default tokenizer behaviour: splitting on whitespace while
     * collapsing runs of consecutive spaces, so {@code "a  b c"} yields exactly
     * the three tokens {@code "a"}, {@code "b"} and {@code "c"}.
     */
    @Test
    void testBasic1() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext(), "no tokens should remain after the last one");
    }
}
