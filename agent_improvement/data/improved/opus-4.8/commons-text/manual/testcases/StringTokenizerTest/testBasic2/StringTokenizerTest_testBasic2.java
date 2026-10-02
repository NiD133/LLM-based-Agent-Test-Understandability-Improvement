package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the default {@link StringTokenizer} splits on the standard
 * whitespace characters (space, newline and form feed), exactly like
 * {@link java.util.StringTokenizer}.
 */
public class StringTokenizerTest_testBasic2 {

    @Test
    void testBasic2() {
        // Tokens "a", "b" and "c" are separated by a space+newline run and a form feed.
        final String input = "a \nb\fc";
        final StringTokenizer tokenizer = new StringTokenizer(input);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext(), "all three tokens should be consumed");
    }
}
