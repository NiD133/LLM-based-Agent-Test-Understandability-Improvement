package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_paddingInACan {
    private static final int EXPECTED_PADDING_CACHE_SIZE = 21;

    @Test
    public void paddingInACan() {
        String[] cachedPaddingByWidth = StringUtil.padding;

        assertEquals(EXPECTED_PADDING_CACHE_SIZE, cachedPaddingByWidth.length);
        for (int width = 0; width < cachedPaddingByWidth.length; width++) {
            assertEquals(width, cachedPaddingByWidth[width].length());
        }
    }
}
