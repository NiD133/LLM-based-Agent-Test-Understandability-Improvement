package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link StringTokenizer#StringTokenizer(String)} constructor.
 * <p>
 * By default the tokenizer splits on whitespace (space, tab, newline, form feed),
 * just like {@link java.util.StringTokenizer}.
 */
public class StringTokenizerTest_testConstructor_String {

    @Test
    void testConstructor_String() {
        // A normal input is split on whitespace into individual tokens.
        StringTokenizer tokenizer = new StringTokenizer("a b");
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext(), "no tokens should remain after the last one");

        // An empty input yields no tokens.
        tokenizer = new StringTokenizer("");
        assertFalse(tokenizer.hasNext(), "empty input should produce no tokens");

        // A null input is treated as no text to parse and yields no tokens.
        tokenizer = new StringTokenizer((String) null);
        assertFalse(tokenizer.hasNext(), "null input should produce no tokens");
    }
}
