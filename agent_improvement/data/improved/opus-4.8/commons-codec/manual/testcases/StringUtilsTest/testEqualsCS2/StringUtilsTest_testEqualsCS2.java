package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#equals(CharSequence, CharSequence)} when comparing
 * across different {@link CharSequence} implementations (a {@link String} versus
 * a {@link StringBuilder}).
 *
 * <p>
 * The comparison is content-based and case-sensitive: two sequences are equal
 * only when they hold exactly the same characters in the same order, regardless
 * of their concrete {@code CharSequence} type.
 * </p>
 */
public class StringUtilsTest_testEqualsCS2 {

    @Test
    void testEqualsCS2() {
        // Same characters, different CharSequence types -> equal.
        assertTrue(StringUtils.equals("abc", new StringBuilder("abc")));

        // The StringBuilder "abc" is a prefix of "abcd": different length -> not equal.
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "abcd"));

        // "abcd" is longer than the StringBuilder "abc": different length -> not equal.
        assertFalse(StringUtils.equals("abcd", new StringBuilder("abc")));

        // Same letters but different case ("abc" vs "ABC"): case-sensitive -> not equal.
        assertFalse(StringUtils.equals(new StringBuilder("abc"), "ABC"));
    }
}
