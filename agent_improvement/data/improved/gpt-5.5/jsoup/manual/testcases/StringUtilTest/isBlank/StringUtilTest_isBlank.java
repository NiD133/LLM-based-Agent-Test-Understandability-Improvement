package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_isBlank {

    @Test
    public void isBlank() {
        // Null, empty, and whitespace-only strings are blank.
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isBlank("      "));
        assertTrue(StringUtil.isBlank("   \r\n  "));

        // Any non-whitespace content makes the string non-blank.
        assertFalse(StringUtil.isBlank("hello"));
        assertFalse(StringUtil.isBlank("   hello   "));
    }
}
