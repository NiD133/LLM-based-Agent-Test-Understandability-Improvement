package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_String_char_char {

    @Test
    void testConstructor_String_char_char() {
        StringTokenizer tok = new StringTokenizer("a b", ' ', '"');

        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ", 0, 0, 1));
        assertEquals(1, tok.getQuoteMatcher().isMatch("\"".toCharArray(), 0, 0, 1));
        assertEquals(1, tok.getQuoteMatcher().isMatch("\"", 0, 0, 1));

        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertFalse(tok.hasNext());

        tok = new StringTokenizer("", ' ', '"');
        assertFalse(tok.hasNext());

        tok = new StringTokenizer((String) null, ' ', '"');
        assertFalse(tok.hasNext());
    }
}
