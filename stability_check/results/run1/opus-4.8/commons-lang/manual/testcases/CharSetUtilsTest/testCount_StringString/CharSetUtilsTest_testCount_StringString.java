package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#count(String, String...)}.
 *
 * <p>{@code count} returns how many characters of the input string belong to the
 * given character set (expressed in set-syntax, e.g. {@code "a-e"}).</p>
 */
public class CharSetUtilsTest_testCount_StringString extends AbstractLangTest {

    @Test
    void testCount_StringString() {
        // A null or empty input string always yields a count of zero,
        // regardless of the character set.
        assertEquals(0, CharSetUtils.count(null, (String) null));
        assertEquals(0, CharSetUtils.count(null, ""));
        assertEquals(0, CharSetUtils.count("", (String) null));
        assertEquals(0, CharSetUtils.count("", ""));
        assertEquals(0, CharSetUtils.count("", "a-e"));

        // A null or empty character set matches nothing, so the count is zero.
        assertEquals(0, CharSetUtils.count("hello", (String) null));
        assertEquals(0, CharSetUtils.count("hello", ""));

        // "hello" has one character in the range a-e (the 'e').
        assertEquals(1, CharSetUtils.count("hello", "a-e"));

        // "hello" has three characters in the range l-p (two 'l's and the 'o').
        assertEquals(3, CharSetUtils.count("hello", "l-p"));
    }
}
