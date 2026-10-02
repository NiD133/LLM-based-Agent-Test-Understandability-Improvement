package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_charArray_char {

    @Test
    void testConstructor_charArray_char() {
        // A non-empty char array with a space delimiter should produce two tokens
        StringTokenizer tok = new StringTokenizer("a b".toCharArray(), ' ');
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ", 0, 0, 1));
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertFalse(tok.hasNext());

        // An empty char array should produce no tokens
        tok = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY, ' ');
        assertFalse(tok.hasNext());

        // A null char array should produce no tokens
        tok = new StringTokenizer((char[]) null, ' ');
        assertFalse(tok.hasNext());
    }
}
