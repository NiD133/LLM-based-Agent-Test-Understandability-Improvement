package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_String_char {

    @Test
    void testConstructor_String_char() {
        // Construct a tokenizer with a space as the delimiter
        StringTokenizer tok = new StringTokenizer("a b", ' ');

        // Verify the delimiter matcher recognises a single space in both overloads
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ", 0, 0, 1));

        // The input "a b" should produce two tokens: "a" and "b"
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertFalse(tok.hasNext());

        // An empty input string should produce no tokens
        tok = new StringTokenizer("", ' ');
        assertFalse(tok.hasNext());

        // A null input string should also produce no tokens
        tok = new StringTokenizer((String) null, ' ');
        assertFalse(tok.hasNext());
    }
}
