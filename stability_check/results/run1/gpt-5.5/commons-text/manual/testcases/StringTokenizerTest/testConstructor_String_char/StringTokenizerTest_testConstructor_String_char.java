package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_String_char {

    private static final char SPACE_DELIMITER = ' ';
    private static final String SPACE = " ";

    @Test
    void testConstructor_String_char() {
        assertSpaceDelimitedInputIsTokenized();
        assertInputWithoutTokensHasNoNextToken("");
        assertInputWithoutTokensHasNoNextToken(null);
    }

    private void assertSpaceDelimitedInputIsTokenized() {
        final StringTokenizer tokenizer = new StringTokenizer("a b", SPACE_DELIMITER);

        assertSpaceDelimiterMatcher(tokenizer);
        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }

    private void assertSpaceDelimiterMatcher(final StringTokenizer tokenizer) {
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(SPACE.toCharArray(), 0, 0, 1));
        assertEquals(1, tokenizer.getDelimiterMatcher().isMatch(SPACE, 0, 0, 1));
    }

    private void assertInputWithoutTokensHasNoNextToken(final String input) {
        final StringTokenizer tokenizer = new StringTokenizer(input, SPACE_DELIMITER);

        assertFalse(tokenizer.hasNext());
    }
}
