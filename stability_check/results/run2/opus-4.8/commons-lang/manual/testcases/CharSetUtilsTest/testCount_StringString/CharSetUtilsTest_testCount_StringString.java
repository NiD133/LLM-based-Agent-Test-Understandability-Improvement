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
        // A null or empty input string always counts zero, regardless of the set.
        assertEquals(0, CharSetUtils.count(null, (String) null));
        assertEquals(0, CharSetUtils.count(null, ""));
        assertEquals(0, CharSetUtils.count("", (String) null));
        assertEquals(0, CharSetUtils.count("", ""));
        assertEquals(0, CharSetUtils.count("", "a-e"));

        // A null or empty set means no characters can match, so the count is zero.
        assertEquals(0, CharSetUtils.count("hello", (String) null));
        assertEquals(0, CharSetUtils.count("hello", ""));

        // Only 'e' of "hello" falls in the range a-e, so the count is 1.
        assertEquals(1, CharSetUtils.count("hello", "a-e"));

        // 'l', 'l' and 'o' of "hello" fall in the range l-p, so the count is 3.
        assertEquals(3, CharSetUtils.count("hello", "l-p"));
    }
}
