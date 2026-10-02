package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils#containsAny(String, String...)}.
 *
 * <p>{@code containsAny} reports whether the input string contains at least one
 * character that falls within the given set-syntax range (e.g. {@code "a-e"}).</p>
 */
public class CharSetUtilsTest_testContainsAny_StringString extends AbstractLangTest {

    @Test
    void testContainsAny_StringString() {
        // A null or empty input string can never contain any character, regardless of the set.
        assertFalse(CharSetUtils.containsAny(null, (String) null), "null string, null set");
        assertFalse(CharSetUtils.containsAny(null, ""), "null string, empty set");
        assertFalse(CharSetUtils.containsAny("", (String) null), "empty string, null set");
        assertFalse(CharSetUtils.containsAny("", ""), "empty string, empty set");
        assertFalse(CharSetUtils.containsAny("", "a-e"), "empty string, non-empty set");

        // A null or empty set contains no characters to match, so nothing can be found.
        assertFalse(CharSetUtils.containsAny("hello", (String) null), "non-empty string, null set");
        assertFalse(CharSetUtils.containsAny("hello", ""), "non-empty string, empty set");

        // "hello" shares at least one character with each range below, so the result is true.
        assertTrue(CharSetUtils.containsAny("hello", "a-e"), "'e' is within range a-e");
        assertTrue(CharSetUtils.containsAny("hello", "l-p"), "'l' and 'o' are within range l-p");
    }
}
