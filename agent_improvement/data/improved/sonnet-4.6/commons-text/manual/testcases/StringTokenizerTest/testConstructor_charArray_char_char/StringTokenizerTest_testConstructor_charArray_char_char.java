package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_charArray_char_char {

    @Test
    void testConstructor_charArray_char_char() {
        // Verify that delimiter and quote matchers are configured from the constructor arguments
        StringTokenizer tok = new StringTokenizer("a b".toCharArray(), ' ', '"');
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ", 0, 0, 1));
        assertEquals(1, tok.getQuoteMatcher().isMatch("\"".toCharArray(), 0, 0, 1));
        assertEquals(1, tok.getQuoteMatcher().isMatch("\"", 0, 0, 1));

        // Verify that the input is correctly tokenized using the specified delimiter
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertFalse(tok.hasNext());

        // An empty char array produces a tokenizer with no tokens
        tok = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY, ' ', '"');
        assertFalse(tok.hasNext());

        // A null char array also produces a tokenizer with no tokens
        tok = new StringTokenizer((char[]) null, ' ', '"');
        assertFalse(tok.hasNext());
    }
}
