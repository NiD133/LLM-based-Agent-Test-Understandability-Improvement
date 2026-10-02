package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testEqualsCS1 {

    @Test
    void testEqualsCS1() {
        assertFalse(StringUtils.equals(new StringBuilder("abc"), null));
        assertFalse(StringUtils.equals(null, new StringBuilder("abc")));

        assertTrue(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abc")));

        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abcd")));
        assertFalse(StringUtils.equals(new StringBuilder("abcd"), new StringBuilder("abc")));
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("ABC")));
    }
}
