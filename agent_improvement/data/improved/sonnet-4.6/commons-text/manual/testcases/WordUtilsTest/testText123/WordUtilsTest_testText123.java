package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testText123 {

    // A long input string with three space-separated tokens, none of which fits within any single line
    // when wrapLength is Integer.MAX_VALUE. This exercises the boundary arithmetic in WordUtils.wrap
    // and previously caused a StringIndexOutOfBoundsException before the bug fix.
    private static final String LONG_WORD_TEXT =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @Test
    void testText123() {
        // Regression: wrapping with Integer.MAX_VALUE must not throw StringIndexOutOfBoundsException
        assertDoesNotThrow(() -> WordUtils.wrap(LONG_WORD_TEXT, Integer.MAX_VALUE));
    }
}
