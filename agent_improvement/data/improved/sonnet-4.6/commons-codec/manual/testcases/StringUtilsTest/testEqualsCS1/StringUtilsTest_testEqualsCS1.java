package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringUtilsTest_testEqualsCS1 {

    @Test
    @DisplayName("equals(CharSequence, CharSequence) is case-sensitive and handles nulls")
    void testEqualsCS1() {
        CharSequence abc = new StringBuilder("abc");
        CharSequence abcd = new StringBuilder("abcd");
        CharSequence ABC = new StringBuilder("ABC");

        // null handling: one side null always returns false
        assertFalse(StringUtils.equals(abc, null));
        assertFalse(StringUtils.equals(null, abc));

        // equal content → true
        assertTrue(StringUtils.equals(abc, new StringBuilder("abc")));

        // different lengths → false
        assertFalse(StringUtils.equals(abc, abcd));
        assertFalse(StringUtils.equals(abcd, abc));

        // same length but different case → false (comparison is case-sensitive)
        assertFalse(StringUtils.equals(abc, ABC));
    }
}
