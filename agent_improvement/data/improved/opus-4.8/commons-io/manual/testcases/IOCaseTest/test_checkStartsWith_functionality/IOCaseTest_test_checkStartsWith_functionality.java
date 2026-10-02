package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#checkStartsWith(String, String)} for the case-sensitive
 * variant ({@link IOCase#SENSITIVE}).
 */
public class IOCaseTest_test_checkStartsWith_functionality {

    @Test
    void test_checkStartsWith_functionality() {
        // Every string starts with the empty prefix, including the empty string itself.
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", ""));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("", ""));

        // Any leading portion of the string is a valid prefix, up to the whole string.
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "A"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "ABC"));

        // A substring that is not at the start is not a prefix.
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "BC"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "C"));

        // A prefix longer than the string can never match.
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "ABCD"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("", "ABC"));

        // Null inputs always yield false.
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", null));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, null));
    }
}
