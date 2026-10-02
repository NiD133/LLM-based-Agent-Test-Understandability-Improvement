package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_isAscii {
    private static final String EMPTY_STRING = "";
    private static final String ASCII_DOMAIN = "example.com";
    private static final String ASCII_WORDS_WITH_SPACE = "One Two";
    private static final String EMOJI = "🧔";
    private static final String NON_ASCII_TEXT = "测试";
    private static final String NON_ASCII_DOMAIN = "测试.com";

    @Test
    void isAscii() {
        assertTrue(StringUtil.isAscii(EMPTY_STRING));
        assertTrue(StringUtil.isAscii(ASCII_DOMAIN));
        assertTrue(StringUtil.isAscii(ASCII_WORDS_WITH_SPACE));

        assertFalse(StringUtil.isAscii(EMOJI));
        assertFalse(StringUtil.isAscii(NON_ASCII_TEXT));
        assertFalse(StringUtil.isAscii(NON_ASCII_DOMAIN));
    }
}
