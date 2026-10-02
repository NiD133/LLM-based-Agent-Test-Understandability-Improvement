package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testContainsAny_StringStringarray extends AbstractLangTest {

    // containsAny(null, *) must always return false regardless of the set argument
    @Test
    void containsAny_returnsFalse_whenStringIsNull() {
        assertFalse(CharSetUtils.containsAny(null, (String[]) null));
        assertFalse(CharSetUtils.containsAny(null));
        assertFalse(CharSetUtils.containsAny(null, (String) null));
        assertFalse(CharSetUtils.containsAny(null, "a-e"));
    }

    // containsAny("", *) must always return false regardless of the set argument
    @Test
    void containsAny_returnsFalse_whenStringIsEmpty() {
        assertFalse(CharSetUtils.containsAny("", (String[]) null));
        assertFalse(CharSetUtils.containsAny(""));
        assertFalse(CharSetUtils.containsAny("", (String) null));
        assertFalse(CharSetUtils.containsAny("", "a-e"));
    }

    // containsAny(str, null/empty) must return false regardless of the string content
    @Test
    void containsAny_returnsFalse_whenSetIsNullOrEmpty() {
        assertFalse(CharSetUtils.containsAny("hello", (String[]) null));
        assertFalse(CharSetUtils.containsAny("hello"));
        assertFalse(CharSetUtils.containsAny("hello", (String) null));
        assertFalse(CharSetUtils.containsAny("hello", ""));
    }

    // "hello" contains 'e' which falls within the range "a-e", "e-i", and "a-z"
    @Test
    void containsAny_returnsTrue_whenStringContainsCharMatchingRange() {
        assertTrue(CharSetUtils.containsAny("hello", "a-e"));
        assertTrue(CharSetUtils.containsAny("hello", "e-i"));
        assertTrue(CharSetUtils.containsAny("hello", "a-z"));
    }

    // "hello" contains 'e' and 'l' which are both listed in the explicit set "el"
    @Test
    void containsAny_returnsTrue_whenStringContainsExplicitSetChar() {
        assertTrue(CharSetUtils.containsAny("hello", "el"));
    }

    // "hello" contains none of the characters in "x", so the result is false
    @Test
    void containsAny_returnsFalse_whenStringHasNoMatchingChar() {
        assertFalse(CharSetUtils.containsAny("hello", "x"));
    }
}
