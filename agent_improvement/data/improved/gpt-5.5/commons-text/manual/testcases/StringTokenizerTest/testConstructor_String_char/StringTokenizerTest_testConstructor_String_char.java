package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_String_char {

    @Test
    void testConstructor_String_char() {
        StringTokenizer tokenizer = new StringTokenizer("a b", ' ');

        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(" ", 0, 0, 1));
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer("", ' ');
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((String) null, ' ');
        assertFalse(tokenizer.hasNext());
    }
}
