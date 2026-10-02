package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testEqualsString {

    @Test
    void testEqualsString() {
        // Both null references are considered equal
        assertTrue(StringUtils.equals(null, null));

        // One null and one non-null are never equal
        assertFalse(StringUtils.equals("abc", null));
        assertFalse(StringUtils.equals(null, "abc"));

        // Identical content is equal
        assertTrue(StringUtils.equals("abc", "abc"));

        // Different lengths are not equal
        assertFalse(StringUtils.equals("abc", "abcd"));
        assertFalse(StringUtils.equals("abcd", "abc"));

        // Comparison is case-sensitive
        assertFalse(StringUtils.equals("abc", "ABC"));
    }
}
