package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_charArray_char_char {

    private static final String TWO_TOKEN_INPUT = "a b";
    private static final char SPACE_DELIMITER = ' ';
    private static final char DOUBLE_QUOTE = '"';

    private static final int MATCH_START_INDEX = 0;
    private static final int BUFFER_START_INDEX = 0;
    private static final int BUFFER_END_INDEX = 1;
    private static final int SINGLE_CHARACTER_MATCH_LENGTH = 1;

    @Test
    void testConstructor_charArray_char_char() {
        StringTokenizer tokenizer = new StringTokenizer(TWO_TOKEN_INPUT.toCharArray(), SPACE_DELIMITER, DOUBLE_QUOTE);

        assertEquals(SINGLE_CHARACTER_MATCH_LENGTH,
                tokenizer.getDelimiterMatcher().isMatch(" ".toCharArray(), MATCH_START_INDEX, BUFFER_START_INDEX,
                        BUFFER_END_INDEX));
        assertEquals(SINGLE_CHARACTER_MATCH_LENGTH,
                tokenizer.getDelimiterMatcher().isMatch(" ", MATCH_START_INDEX, BUFFER_START_INDEX, BUFFER_END_INDEX));
        assertEquals(SINGLE_CHARACTER_MATCH_LENGTH,
                tokenizer.getQuoteMatcher().isMatch("\"".toCharArray(), MATCH_START_INDEX, BUFFER_START_INDEX,
                        BUFFER_END_INDEX));
        assertEquals(SINGLE_CHARACTER_MATCH_LENGTH,
                tokenizer.getQuoteMatcher().isMatch("\"", MATCH_START_INDEX, BUFFER_START_INDEX, BUFFER_END_INDEX));

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY, SPACE_DELIMITER, DOUBLE_QUOTE);
        assertFalse(tokenizer.hasNext());

        tokenizer = new StringTokenizer((char[]) null, SPACE_DELIMITER, DOUBLE_QUOTE);
        assertFalse(tokenizer.hasNext());
    }
}
