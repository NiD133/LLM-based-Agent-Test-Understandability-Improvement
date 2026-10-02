package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_String_char_char {

    private static final char DELIMITER = ' ';
    private static final char QUOTE_CHAR = '"';

    @Test
    void testConstructor_String_char_char() {
        // A non-empty string with space delimiter and double-quote quoting
        StringTokenizer tok = new StringTokenizer("a b", DELIMITER, QUOTE_CHAR);

        // The delimiter matcher should recognise a single space character
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, tok.getDelimiterMatcher().isMatch(" ", 0, 0, 1));

        // The quote matcher should recognise a double-quote character
        assertEquals(1, tok.getQuoteMatcher().isMatch("\"".toCharArray(), 0, 0, 1));
        assertEquals(1, tok.getQuoteMatcher().isMatch("\"", 0, 0, 1));

        // Tokenising "a b" with a space delimiter should yield two tokens
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertFalse(tok.hasNext());

        // An empty string produces no tokens
        StringTokenizer emptyTok = new StringTokenizer("", DELIMITER, QUOTE_CHAR);
        assertFalse(emptyTok.hasNext());

        // A null string also produces no tokens
        StringTokenizer nullTok = new StringTokenizer((String) null, DELIMITER, QUOTE_CHAR);
        assertFalse(nullTok.hasNext());
    }
}
