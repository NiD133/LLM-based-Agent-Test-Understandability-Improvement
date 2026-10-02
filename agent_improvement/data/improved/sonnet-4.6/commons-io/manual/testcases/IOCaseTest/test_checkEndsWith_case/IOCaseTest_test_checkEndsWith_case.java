package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkEndsWith_case {

    // On Windows the file system is case-insensitive; on Unix it is case-sensitive.
    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    @DisplayName("checkEndsWith respects case sensitivity rules for SENSITIVE, INSENSITIVE, and SYSTEM modes")
    void test_checkEndsWith_case() {
        // SENSITIVE: exact case must match
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "BC"),
                "SENSITIVE mode: 'ABC' ends with 'BC' (same case) should be true");
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "Bc"),
                "SENSITIVE mode: 'ABC' ends with 'Bc' (different case) should be false");

        // INSENSITIVE: case differences are ignored
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "BC"),
                "INSENSITIVE mode: 'ABC' ends with 'BC' (same case) should be true");
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "Bc"),
                "INSENSITIVE mode: 'ABC' ends with 'Bc' (different case) should be true");

        // SYSTEM: case sensitivity mirrors the current OS (true on Windows, false on Unix)
        assertTrue(IOCase.SYSTEM.checkEndsWith("ABC", "BC"),
                "SYSTEM mode: 'ABC' ends with 'BC' (same case) should always be true");
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEndsWith("ABC", "Bc"),
                "SYSTEM mode: 'ABC' ends with 'Bc' (different case) should match OS case sensitivity"
                        + " (true on Windows, false on Unix)");
    }
}
