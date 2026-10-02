package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#equals(CharSequence, CharSequence)}.
 *
 * <p>
 * {@code equals} performs a null-safe, case-sensitive comparison of two character
 * sequences. The cases below cover null handling, equality, length differences and
 * case sensitivity.
 * </p>
 */
public class StringUtilsTest_testEqualsString {

    @Test
    void testEqualsString() {
        // Two null references are considered equal.
        assertTrue(StringUtils.equals(null, null));

        // A null reference never equals a non-null sequence (either order).
        assertFalse(StringUtils.equals("abc", null));
        assertFalse(StringUtils.equals(null, "abc"));

        // Identical contents are equal.
        assertTrue(StringUtils.equals("abc", "abc"));

        // Different lengths are not equal (either order).
        assertFalse(StringUtils.equals("abc", "abcd"));
        assertFalse(StringUtils.equals("abcd", "abc"));

        // The comparison is case-sensitive.
        assertFalse(StringUtils.equals("abc", "ABC"));
    }
}
