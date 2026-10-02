package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StringUtilsTest_testEqualsCS2 {

    @Test
    void testEqualsCS2() {
        // String and StringBuilder with identical content should be equal
        assertTrue(StringUtils.equals("abc", new StringBuilder("abc")),
                "String and StringBuilder with same content should be equal");

        // StringBuilder is shorter than String — must not be equal
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "abcd"),
                "StringBuilder 'abc' and String 'abcd' differ in length and should not be equal");

        // String is longer than StringBuilder — must not be equal
        assertFalse(StringUtils.equals("abcd", new StringBuilder("abc")),
                "String 'abcd' and StringBuilder 'abc' differ in length and should not be equal");

        // Same length but different case — comparison is case-sensitive, so not equal
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "ABC"),
                "Case-sensitive comparison: 'abc' and 'ABC' should not be equal");
    }
}
