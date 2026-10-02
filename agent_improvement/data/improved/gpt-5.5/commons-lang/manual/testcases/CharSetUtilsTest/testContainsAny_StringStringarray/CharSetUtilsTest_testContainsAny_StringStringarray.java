package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testContainsAny_StringStringarray extends AbstractLangTest {

    @Test
    void testContainsAny_StringStringarray() {
        assertNullStringNeverContainsCharacters();
        assertEmptyStringNeverContainsCharacters();
        assertStringNeverContainsMissingCharacterSets();
        assertStringContainsAnyMatchingCharacterSet();
    }

    private void assertNullStringNeverContainsCharacters() {
        assertFalse(CharSetUtils.containsAny(null, (String[]) null));
        assertFalse(CharSetUtils.containsAny(null));
        assertFalse(CharSetUtils.containsAny(null, (String) null));
        assertFalse(CharSetUtils.containsAny(null, "a-e"));
    }

    private void assertEmptyStringNeverContainsCharacters() {
        assertFalse(CharSetUtils.containsAny("", (String[]) null));
        assertFalse(CharSetUtils.containsAny(""));
        assertFalse(CharSetUtils.containsAny("", (String) null));
        assertFalse(CharSetUtils.containsAny("", "a-e"));
    }

    private void assertStringNeverContainsMissingCharacterSets() {
        assertFalse(CharSetUtils.containsAny("hello", (String[]) null));
        assertFalse(CharSetUtils.containsAny("hello"));
        assertFalse(CharSetUtils.containsAny("hello", (String) null));
        assertFalse(CharSetUtils.containsAny("hello", "x"));
        assertFalse(CharSetUtils.containsAny("hello", ""));
    }

    private void assertStringContainsAnyMatchingCharacterSet() {
        assertTrue(CharSetUtils.containsAny("hello", "a-e"));
        assertTrue(CharSetUtils.containsAny("hello", "el"));
        assertTrue(CharSetUtils.containsAny("hello", "e-i"));
        assertTrue(CharSetUtils.containsAny("hello", "a-z"));
    }
}
