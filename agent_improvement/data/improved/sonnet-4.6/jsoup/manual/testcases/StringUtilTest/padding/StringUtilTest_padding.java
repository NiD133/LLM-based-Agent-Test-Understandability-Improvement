package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_padding {

    // The memoization cache holds pre-built strings for widths 0–20 (21 entries).
    // Requests within that range return directly without consulting maxPaddingWidth.
    // Requests at width >= 21 fall through to the general path where maxPaddingWidth is enforced.

    @Test
    public void paddingDefaultCapAt30() {
        // padding(int) hard-caps output at 30 spaces regardless of requested width
        assertEquals("", StringUtil.padding(0));
        assertEquals(" ", StringUtil.padding(1));
        assertEquals("  ", StringUtil.padding(2));
        assertEquals("               ", StringUtil.padding(15));
        assertEquals("                              ", StringUtil.padding(45)); // 45 requested, 30 returned
    }

    @Test
    public void paddingUnlimitedAllowsExactWidth() {
        // maxPaddingWidth == -1 means no cap; the exact requested width is returned
        assertEquals("", StringUtil.padding(0, -1));
        assertEquals("                    ", StringUtil.padding(20, -1));  // within memo range
        assertEquals("                     ", StringUtil.padding(21, -1)); // first width beyond memo
        assertEquals("                              ", StringUtil.padding(30, -1));
        assertEquals("                                             ", StringUtil.padding(45, -1));
    }

    @Test
    public void paddingZeroMaxProducesEmpty() {
        // maxPaddingWidth == 0 clamps every request to zero-length output
        assertEquals("", StringUtil.padding(0, 0));
        // width 21 is beyond the memo range, so maxPaddingWidth=0 takes effect and clamps to ""
        assertEquals("", StringUtil.padding(21, 0));
    }

    @Test
    public void paddingExplicitMax30BehavesLikeDefault() {
        // Explicitly passing 30 matches the behaviour of the single-argument overload
        assertEquals("", StringUtil.padding(0, 30));
        assertEquals(" ", StringUtil.padding(1, 30));
        assertEquals("  ", StringUtil.padding(2, 30));
        assertEquals("               ", StringUtil.padding(15, 30));
        assertEquals("                              ", StringUtil.padding(45, 30)); // 45 capped to 30
    }

    @Test
    public void maxPaddingWidthEnforcedEvenForMemoizedWidths() {
        // maxPaddingWidth applies to all widths, including those in the memo range (0–20)
        assertEquals(5, StringUtil.padding(20, 5).length());
    }
}
