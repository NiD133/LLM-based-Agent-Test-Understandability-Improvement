package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testLANG1292 {

    // Three words whose combined length exceeds the wrap column, reproducing the
    // StringIndexOutOfBoundsException that was fixed in LANG-1292.
    private static final String LONG_WORDS_INPUT =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    private static final int WRAP_COLUMN = 70;

    @Test
    void testLANG1292() {
        // Prior to the fix, WordUtils.wrap threw StringIndexOutOfBoundsException
        // when every word in the input was longer than the wrap column.
        assertDoesNotThrow(() -> WordUtils.wrap(LONG_WORDS_INPUT, WRAP_COLUMN));
    }
}
