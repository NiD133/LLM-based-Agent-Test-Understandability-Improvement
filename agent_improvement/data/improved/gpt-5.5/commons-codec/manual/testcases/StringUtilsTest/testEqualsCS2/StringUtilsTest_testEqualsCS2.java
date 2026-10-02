package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testEqualsCS2 {

    @Test
    void testEqualsCS2() {
        assertTrue(StringUtils.equals("abc", new StringBuilder("abc")));

        assertFalse(StringUtils.equals(new StringBuilder("abc"), "abcd"));
        assertFalse(StringUtils.equals("abcd", new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "ABC"));
    }
}
