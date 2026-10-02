package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_isAscii {

    @Test
    void emptyStringIsAscii() {
        assertTrue(StringUtil.isAscii(""));
    }

    @Test
    void pureAsciiStringsAreAscii() {
        assertTrue(StringUtil.isAscii("example.com"));
        assertTrue(StringUtil.isAscii("One Two"));
    }

    @Test
    void emojiIsNotAscii() {
        assertFalse(StringUtil.isAscii("🧔"));
    }

    @Test
    void chineseCharactersAreNotAscii() {
        assertFalse(StringUtil.isAscii("测试"));
    }

    @Test
    void stringMixingNonAsciiAndAsciiIsNotAscii() {
        assertFalse(StringUtil.isAscii("测试.com"));
    }
}
