package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_String_char {

    private static final char SPACE_DELIMITER = ' ';
    private static final String SPACE_DELIMITER_AS_STRING = " ";
    private static final char[] SPACE_DELIMITER_AS_CHARS = SPACE_DELIMITER_AS_STRING.toCharArray();

    @Test
    void testConstructor_String_char() {
        StringTokenizer tokenizer = new StringTokenizer("a b", SPACE_DELIMITER);

        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(SPACE_DELIMITER_AS_CHARS, 0, 0, 1));
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(SPACE_DELIMITER_AS_STRING, 0, 0, 1));
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer("", SPACE_DELIMITER);
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((String) null, SPACE_DELIMITER);
        assertFalse(tokenizer.hasNext());
    }
}
