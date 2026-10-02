package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#checkStartsWith(String, String)} for each case-sensitivity mode.
 */
public class IOCaseTest_test_checkStartsWith_case {

    /** True when running on a (case-insensitive) Windows file system. */
    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkStartsWith_case() {
        // SENSITIVE: the prefix must match exactly, including letter case.
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"), "exact-case prefix should match");
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "Ab"), "differing case must not match");

        // INSENSITIVE: letter case is ignored, so both prefixes match.
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "AB"), "exact-case prefix should match");
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "Ab"), "differing case should still match");

        // SYSTEM: behaves like the current OS file system.
        assertTrue(IOCase.SYSTEM.checkStartsWith("ABC", "AB"), "exact-case prefix should match");
        // Case-insensitive only on Windows; case-sensitive elsewhere.
        assertEquals(WINDOWS, IOCase.SYSTEM.checkStartsWith("ABC", "Ab"),
                "differing-case match depends on the OS file system");
    }
}
