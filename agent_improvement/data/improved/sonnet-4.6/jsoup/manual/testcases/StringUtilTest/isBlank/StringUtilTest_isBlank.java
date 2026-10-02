package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_isBlank {

    @Test
    public void isBlank_returnsTrue_forNull() {
        assertTrue(StringUtil.isBlank(null));
    }

    @Test
    public void isBlank_returnsTrue_forEmptyString() {
        assertTrue(StringUtil.isBlank(""));
    }

    @Test
    public void isBlank_returnsTrue_forSpacesOnly() {
        assertTrue(StringUtil.isBlank("      "));
    }

    @Test
    public void isBlank_returnsTrue_forWhitespaceWithNewlineAndCarriageReturn() {
        assertTrue(StringUtil.isBlank("   \r\n  "));
    }

    @Test
    public void isBlank_returnsFalse_forPlainWord() {
        assertFalse(StringUtil.isBlank("hello"));
    }

    @Test
    public void isBlank_returnsFalse_forWordSurroundedBySpaces() {
        assertFalse(StringUtil.isBlank("   hello   "));
    }
}
