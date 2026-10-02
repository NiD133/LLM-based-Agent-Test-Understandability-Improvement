package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_isNumeric {

    @Test
    public void isNumeric() {
        assertFalse(StringUtil.isNumeric(null), "null is not numeric");
        assertFalse(StringUtil.isNumeric(" "), "whitespace is not numeric");
        assertFalse(StringUtil.isNumeric("123 546"), "embedded whitespace is not numeric");
        assertFalse(StringUtil.isNumeric("hello"), "letters are not numeric");
        assertFalse(StringUtil.isNumeric("123.334"), "decimal punctuation is not numeric");

        assertTrue(StringUtil.isNumeric("1"), "a single digit is numeric");
        assertTrue(StringUtil.isNumeric("1234"), "multiple digits are numeric");
    }
}
