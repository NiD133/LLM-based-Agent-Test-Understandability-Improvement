package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_paddingInACan {

    // StringUtil.padding is a pre-built cache of space strings where padding[n] contains exactly n spaces.
    // This avoids allocating new strings for common indentation widths (0–20).

    @Test
    public void paddingCacheHasExactly21Entries() {
        // The cache covers widths 0 through 20 inclusive, so exactly 21 entries are expected.
        assertEquals(21, StringUtil.padding.length,
            "padding cache must cover widths 0–20 (21 entries)");
    }

    @Test
    public void eachPaddingEntryLengthMatchesItsIndex() {
        // padding[i] must consist of exactly i space characters so that StringUtil.padding(i) returns
        // a string of the requested width directly from the cache without any allocation.
        String[] padding = StringUtil.padding;
        for (int i = 0; i < padding.length; i++) {
            assertEquals(i, padding[i].length(),
                "padding[" + i + "] should have length " + i);
        }
    }
}
