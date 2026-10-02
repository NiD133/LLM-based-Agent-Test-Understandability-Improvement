package org.apache.commons.text;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testText123 {

    private static final String LONG_WORD =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static final String TRAILING_WORD =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static final String TEXT_WITH_LONG_WORDS =
            LONG_WORD + " " + LONG_WORD + " " + TRAILING_WORD;

    @Test
    void testText123() throws Exception {
        // Regression check: wrapping with a very large width must not throw.
        WordUtils.wrap(TEXT_WITH_LONG_WORDS, Integer.MAX_VALUE);
    }
}
