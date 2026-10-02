package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests {@link StringUtil#isAscii(String)}, which reports whether every character of a string
 * falls within the ASCII range (code points 0 - 127).
 */
public class StringUtilTest_isAscii {

    @Test
    void returnsTrueWhenAllCharactersAreAscii() {
        assertTrue(StringUtil.isAscii(""), "empty string contains no non-ASCII characters");
        assertTrue(StringUtil.isAscii("example.com"), "lowercase letters and a dot are ASCII");
        assertTrue(StringUtil.isAscii("One Two"), "letters and a space are ASCII");
    }

    @Test
    void returnsFalseWhenAnyCharacterIsNonAscii() {
        assertFalse(StringUtil.isAscii("🧔"), "emoji is outside the ASCII range");
        assertFalse(StringUtil.isAscii("测试"), "Chinese characters are outside the ASCII range");
        assertFalse(StringUtil.isAscii("测试.com"), "a single non-ASCII character makes the whole string non-ASCII");
    }
}
