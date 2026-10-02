package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * Regression test for {@link WordUtils#wrap(String, int)}.
 *
 * <p>Using {@link Integer#MAX_VALUE} as the wrap length used to overflow an internal
 * index calculation (offset + wrapLength + 1) and throw a
 * {@code StringIndexOutOfBoundsException}. The fix clamps the computed bound, so wrapping
 * must now complete normally regardless of how large the wrap length is.</p>
 */
public class WordUtilsTest_testText123 {

    /** Three space-separated runs of 'a' (43 + 43 + 33 characters). */
    private static final String TEXT =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "
          + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

    @Test
    void wrapWithMaxIntWrapLengthDoesNotOverflow() {
        // Wrap length larger than the text length must not trigger an index overflow.
        assertDoesNotThrow(() -> WordUtils.wrap(TEXT, Integer.MAX_VALUE));
    }
}
