package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringUtilTest_normaliseWhiteSpace {

    @Test
    public void normaliseWhiteSpace() {
        assertNormalisesTo(" ", "    \r \n \r\n");
        assertNormalisesTo(" hello there ", "   hello   \r \n  there    \n");
        assertNormalisesTo("hello", "hello");
        assertNormalisesTo("hello there", "hello\nthere");
    }

    private static void assertNormalisesTo(String expected, String input) {
        assertEquals(expected, normaliseWhitespace(input));
    }
}
