package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#containsAny(String, String...)}.
 *
 * <p>{@code containsAny} reports whether the input string contains at least one
 * character that belongs to the given set (expressed in CharSet set-syntax,
 * e.g. {@code "a-e"} for the range a..e). It is null-safe and treats a null /
 * empty string and a null / empty set as "no match".</p>
 */
public class CharSetUtilsTest_testContainsAny_StringStringarray extends AbstractLangTest {

    @Test
    void testContainsAny_StringStringarray() {
        // A null input string never matches, regardless of the set.
        assertFalse(CharSetUtils.containsAny(null, (String[]) null), "null string, null array");
        assertFalse(CharSetUtils.containsAny(null), "null string, no set");
        assertFalse(CharSetUtils.containsAny(null, (String) null), "null string, null set");
        assertFalse(CharSetUtils.containsAny(null, "a-e"), "null string, non-empty set");

        // An empty input string never matches, regardless of the set.
        assertFalse(CharSetUtils.containsAny("", (String[]) null), "empty string, null array");
        assertFalse(CharSetUtils.containsAny(""), "empty string, no set");
        assertFalse(CharSetUtils.containsAny("", (String) null), "empty string, null set");
        assertFalse(CharSetUtils.containsAny("", "a-e"), "empty string, non-empty set");

        // A null or empty set never matches, regardless of the string.
        assertFalse(CharSetUtils.containsAny("hello", (String[]) null), "non-empty string, null array");
        assertFalse(CharSetUtils.containsAny("hello"), "non-empty string, no set");
        assertFalse(CharSetUtils.containsAny("hello", (String) null), "non-empty string, null set");
        assertFalse(CharSetUtils.containsAny("hello", ""), "non-empty string, empty set");

        // With a real set, the result reflects whether any character overlaps.
        assertTrue(CharSetUtils.containsAny("hello", "a-e"), "'e' is in range a-e");
        assertTrue(CharSetUtils.containsAny("hello", "el"), "'e' and 'l' are listed");
        assertTrue(CharSetUtils.containsAny("hello", "e-i"), "'e' and 'h' are in range e-i");
        assertTrue(CharSetUtils.containsAny("hello", "a-z"), "all letters are in range a-z");
        assertFalse(CharSetUtils.containsAny("hello", "x"), "'x' does not occur in hello");
    }
}
