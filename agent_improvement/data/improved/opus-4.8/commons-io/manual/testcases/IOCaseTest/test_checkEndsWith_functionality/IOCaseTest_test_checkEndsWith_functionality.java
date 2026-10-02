package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link IOCase#checkEndsWith(String, String)} for the case-sensitive
 * variant ({@link IOCase#SENSITIVE}).
 */
public class IOCaseTest_test_checkEndsWith_functionality {

    @Test
    void test_checkEndsWith_functionality() {
        // The empty suffix is considered to end every string.
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", ""));

        // A genuine suffix matches; a prefix or mid-string fragment does not.
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "BC"));
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "C"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "A"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "AB"));

        // A suffix longer than the string cannot match.
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "ABCD"));

        // Empty-string edge cases.
        assertFalse(IOCase.SENSITIVE.checkEndsWith("", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkEndsWith("", ""));

        // Any null input yields false.
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", null));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(null, "ABC"));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(null, null));
    }
}
