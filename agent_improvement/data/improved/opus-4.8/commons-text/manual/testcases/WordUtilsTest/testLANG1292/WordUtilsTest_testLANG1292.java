package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Regression test for LANG-1292.
 *
 * <p>The bug: {@link WordUtils#wrap(String, int)} threw a
 * {@link StringIndexOutOfBoundsException} when the text was long enough that the
 * final segment landed past the wrap length. This test guards against that
 * regression by confirming the call completes normally.</p>
 */
public class WordUtilsTest_testLANG1292 {

    /** Three space-separated runs of 'a' that previously triggered the out-of-bounds error. */
    private static final String LONG_TEXT =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
            + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    private static final int WRAP_LENGTH = 70;

    @Test
    void wrappingLongTextDoesNotThrow() {
        // Prior to the LANG-1292 fix this threw StringIndexOutOfBoundsException.
        assertDoesNotThrow(() -> WordUtils.wrap(LONG_TEXT, WRAP_LENGTH));
    }
}
