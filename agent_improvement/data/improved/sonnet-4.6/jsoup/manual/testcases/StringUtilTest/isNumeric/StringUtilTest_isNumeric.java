package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_isNumeric {

    @Test
    public void isNumericReturnsFalseForNull() {
        assertFalse(StringUtil.isNumeric(null));
    }

    @Test
    public void isNumericReturnsFalseForBlankString() {
        assertFalse(StringUtil.isNumeric(" "));
    }

    @Test
    public void isNumericReturnsFalseForStringWithInternalSpace() {
        assertFalse(StringUtil.isNumeric("123 546"));
    }

    @Test
    public void isNumericReturnsFalseForAlphabeticString() {
        assertFalse(StringUtil.isNumeric("hello"));
    }

    @Test
    public void isNumericReturnsFalseForDecimalNumber() {
        assertFalse(StringUtil.isNumeric("123.334"));
    }

    @Test
    public void isNumericReturnsTrueForSingleDigit() {
        assertTrue(StringUtil.isNumeric("1"));
    }

    @Test
    public void isNumericReturnsTrueForMultipleDigits() {
        assertTrue(StringUtil.isNumeric("1234"));
    }
}
