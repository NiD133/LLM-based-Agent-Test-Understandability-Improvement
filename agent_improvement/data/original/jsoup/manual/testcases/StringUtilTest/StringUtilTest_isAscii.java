package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_isAscii {

    @Test
    void isAscii() {
        assertTrue(StringUtil.isAscii(""));
        assertTrue(StringUtil.isAscii("example.com"));
        assertTrue(StringUtil.isAscii("One Two"));
        assertFalse(StringUtil.isAscii("🧔"));
        assertFalse(StringUtil.isAscii("测试"));
        assertFalse(StringUtil.isAscii("测试.com"));
    }
}
