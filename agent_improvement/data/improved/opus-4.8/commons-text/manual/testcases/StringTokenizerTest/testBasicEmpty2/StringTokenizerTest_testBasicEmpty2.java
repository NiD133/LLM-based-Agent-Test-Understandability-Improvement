package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicEmpty2 {

    /**
     * When empty tokens are kept and reported as {@code null}, the gap created by
     * the double space in {@code "a  b c"} should surface as a single {@code null}
     * token between {@code "a"} and {@code "b"}.
     */
    @Test
    void testBasicEmpty2() {
        final String input = "a  b c";
        final StringTokenizer tokenizer = new StringTokenizer(input);
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertNull(tokenizer.next(), "the double space yields an empty token reported as null");
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext(), "all tokens have been consumed");
    }
}
