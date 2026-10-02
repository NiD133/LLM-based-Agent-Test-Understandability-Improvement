package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkStartsWith_functionality {

    @Test
    void test_checkStartsWith_functionality() {
        // Positive: "ABC" starts with these valid prefixes (empty, partial, full match)
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", ""));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "A"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "ABC"));

        // Negative: "ABC" does not start with these strings
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "BC"));   // middle substring
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "C"));    // suffix only
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "ABCD")); // prefix longer than str

        // Edge: empty string as the subject
        assertFalse(IOCase.SENSITIVE.checkStartsWith("", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkStartsWith("", ""));

        // Null safety: any null argument returns false
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", null));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, null));
    }
}
