package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies the memoised {@link StringUtil#padding} cache, which stores pre-built
 * strings of spaces so that common indentation widths don't need to be rebuilt.
 */
public class StringUtilTest_paddingInACan {

    /** The cache is expected to hold entries for widths 0 through 20 inclusive. */
    private static final int EXPECTED_PADDING_COUNT = 21;

    @Test
    public void paddingCacheHasOneEntryPerWidthFromZeroToTwenty() {
        String[] padding = StringUtil.padding;

        assertEquals(EXPECTED_PADDING_COUNT, padding.length,
            "padding cache should contain entries for widths 0..20");

        // The entry at index i should be a string of exactly i spaces.
        for (int width = 0; width < padding.length; width++) {
            assertEquals(width, padding[width].length(),
                "padding[" + width + "] should be a string of " + width + " space(s)");
        }
    }
}
