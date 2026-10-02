package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted1 {

    /**
     * Tokenizing a space-delimited string with single quotes as the quote
     * character should strip the quotes around {@code 'b'} and yield the three
     * tokens {@code a}, {@code b} and {@code c}.
     */
    @Test
    void testBasicQuoted1() {
        final String input = "a 'b' c";
        final char delimiter = ' ';
        final char quote = '\'';
        final StringTokenizer tokenizer = new StringTokenizer(input, delimiter, quote);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next(), "quotes around 'b' should be removed");
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext(), "all tokens should be consumed");
    }
}
