package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_String_char {

    @Test
    void testConstructor_String_char() {
        // Verify that the char delimiter is registered correctly in the delimiter matcher
        StringTokenizer tok = new StringTokenizer("a b", ' ');
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ", 0, 0, 1));

        // Verify the tokenizer splits on the space delimiter and produces two tokens
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertFalse(tok.hasNext());

        // An empty input string should yield no tokens
        tok = new StringTokenizer("", ' ');
        assertFalse(tok.hasNext());

        // A null input string should yield no tokens
        tok = new StringTokenizer((String) null, ' ');
        assertFalse(tok.hasNext());
    }
}
