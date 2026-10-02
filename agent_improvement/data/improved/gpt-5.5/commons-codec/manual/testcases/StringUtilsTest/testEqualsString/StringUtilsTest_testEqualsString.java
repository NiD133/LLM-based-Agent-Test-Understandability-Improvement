package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testEqualsString {

    @Test
    void testEqualsString() {
        assertTrue(StringUtils.equals(null, null), "Two null references should be equal");

        assertFalse(StringUtils.equals("abc", null), "A non-null string should not equal null");
        assertFalse(StringUtils.equals(null, "abc"), "Null should not equal a non-null string");

        assertTrue(StringUtils.equals("abc", "abc"), "Identical strings should be equal");

        assertFalse(StringUtils.equals("abc", "abcd"), "A shorter string should not equal a longer string");
        assertFalse(StringUtils.equals("abcd", "abc"), "A longer string should not equal a shorter string");
        assertFalse(StringUtils.equals("abc", "ABC"), "String comparison should be case-sensitive");
    }
}
