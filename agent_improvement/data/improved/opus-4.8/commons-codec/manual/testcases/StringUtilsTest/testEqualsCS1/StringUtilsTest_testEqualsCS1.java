package org.apache.commons.codec.binary;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link StringUtils#equals(CharSequence, CharSequence)} using non-String
 * CharSequence arguments (StringBuilder), which exercises the character-by-character
 * comparison path rather than String.equals.
 */
public class StringUtilsTest_testEqualsCS1 {

    @Test
    void testEqualsCS1() {
        // A non-null sequence is never equal to null, in either argument position.
        assertFalse(StringUtils.equals(new StringBuilder("abc"), null));
        assertFalse(StringUtils.equals(null, new StringBuilder("abc")));

        // Identical contents compare equal.
        assertTrue(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abc")));

        // Differing length makes the sequences unequal, regardless of which side is longer.
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("abcd")));
        assertFalse(StringUtils.equals(new StringBuilder("abcd"), new StringBuilder("abc")));

        // The comparison is case sensitive.
        assertFalse(StringUtils.equals(new StringBuilder("abc"), new StringBuilder("ABC")));
    }
}
