package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link StringUtil#isNumeric(String)}, which returns true only when a
 * string is non-null, non-empty, and made up entirely of ASCII digit characters.
 */
public class StringUtilTest_isNumeric {

    @Test
    public void rejectsNullEmptyAndNonDigitStrings() {
        // null is not numeric
        assertFalse(StringUtil.isNumeric(null));
        // a blank string contains no digits
        assertFalse(StringUtil.isNumeric(" "));
        // an embedded space breaks the all-digits rule
        assertFalse(StringUtil.isNumeric("123 546"));
        // letters are not digits
        assertFalse(StringUtil.isNumeric("hello"));
        // a decimal point is not an ASCII digit
        assertFalse(StringUtil.isNumeric("123.334"));
    }

    @Test
    public void acceptsStringsOfOnlyDigits() {
        // a single digit is numeric
        assertTrue(StringUtil.isNumeric("1"));
        // multiple digits are numeric
        assertTrue(StringUtil.isNumeric("1234"));
    }
}
