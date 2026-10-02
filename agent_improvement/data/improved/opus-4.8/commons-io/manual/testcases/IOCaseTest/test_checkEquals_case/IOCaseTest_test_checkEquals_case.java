package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#checkEquals(String, String)} for each case-sensitivity mode.
 */
public class IOCaseTest_test_checkEquals_case {

    /**
     * Whether the current platform uses the Windows file separator. On Windows the
     * {@link IOCase#SYSTEM} mode is case-insensitive; on Unix-like systems it is
     * case-sensitive. This drives the expected result for the SYSTEM comparison below.
     */
    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkEquals_case() {
        // SENSITIVE: case must match exactly.
        assertTrue(IOCase.SENSITIVE.checkEquals("ABC", "ABC"), "identical strings are equal");
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "Abc"), "differing case is not equal when case-sensitive");

        // INSENSITIVE: case is ignored.
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "ABC"), "identical strings are equal");
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "Abc"), "differing case is equal when case-insensitive");

        // SYSTEM: behaviour depends on the host operating system.
        assertTrue(IOCase.SYSTEM.checkEquals("ABC", "ABC"), "identical strings are always equal");
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEquals("ABC", "Abc"),
                "differing case is equal only on case-insensitive (Windows) systems");
    }
}
