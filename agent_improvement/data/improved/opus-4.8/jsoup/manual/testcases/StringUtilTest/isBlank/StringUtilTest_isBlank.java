package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link StringUtil#isBlank(String)}.
 * <p>
 * A string is considered blank when it is {@code null}, empty, or made up
 * entirely of whitespace characters. Any visible (non-whitespace) content
 * makes it non-blank.
 */
public class StringUtilTest_isBlank {

    @Test
    public void isBlank() {
        // Blank: null, empty, and whitespace-only strings.
        assertTrue(StringUtil.isBlank(null), "null is blank");
        assertTrue(StringUtil.isBlank(""), "empty string is blank");
        assertTrue(StringUtil.isBlank("      "), "spaces only is blank");
        assertTrue(StringUtil.isBlank("   \r\n  "), "spaces and line breaks only is blank");

        // Not blank: any visible content, even when surrounded by whitespace.
        assertFalse(StringUtil.isBlank("hello"), "visible text is not blank");
        assertFalse(StringUtil.isBlank("   hello   "), "visible text padded with spaces is not blank");
    }
}
