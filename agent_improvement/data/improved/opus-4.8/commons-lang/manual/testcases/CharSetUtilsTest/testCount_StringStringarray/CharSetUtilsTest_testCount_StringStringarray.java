package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#count(String, String...)}.
 *
 * <p>{@code count} reports how many characters of the input string belong to the
 * given character set (expressed in CharSet set-syntax, e.g. {@code "a-e"}).
 * Any {@code null} or empty input — whether the string or the set — yields {@code 0}.</p>
 */
public class CharSetUtilsTest_testCount_StringStringarray extends AbstractLangTest {

    @Test
    void testCount_StringStringarray() {
        // A null input string always counts to zero, regardless of the set.
        assertEquals(0, CharSetUtils.count(null, (String[]) null));
        assertEquals(0, CharSetUtils.count(null));
        assertEquals(0, CharSetUtils.count(null, (String) null));
        assertEquals(0, CharSetUtils.count(null, "a-e"));

        // An empty input string always counts to zero, regardless of the set.
        assertEquals(0, CharSetUtils.count("", (String[]) null));
        assertEquals(0, CharSetUtils.count(""));
        assertEquals(0, CharSetUtils.count("", (String) null));
        assertEquals(0, CharSetUtils.count("", "a-e"));

        // A null or empty set means there is nothing to match, so the count is zero.
        assertEquals(0, CharSetUtils.count("hello", (String[]) null));
        assertEquals(0, CharSetUtils.count("hello"));
        assertEquals(0, CharSetUtils.count("hello", (String) null));
        assertEquals(0, CharSetUtils.count("hello", ""));

        // Counting characters of "hello" that fall inside various sets.
        assertEquals(1, CharSetUtils.count("hello", "a-e")); // only 'e'
        assertEquals(3, CharSetUtils.count("hello", "el"));  // 'e', 'l', 'l'
        assertEquals(0, CharSetUtils.count("hello", "x"));    // no matches
        assertEquals(2, CharSetUtils.count("hello", "e-i")); // 'e', 'h'
        assertEquals(5, CharSetUtils.count("hello", "a-z")); // every character
    }
}
