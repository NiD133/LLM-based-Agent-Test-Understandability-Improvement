package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_isCaseSensitive {

    // On Windows, the file separator is '\', making it case-insensitive by convention.
    // On Unix/Linux/macOS, the file separator is '/', and the file system is case-sensitive.
    private static final boolean IS_CASE_SENSITIVE_OS = File.separatorChar != '\\';

    @Test
    void test_isCaseSensitive() {
        assertTrue(IOCase.SENSITIVE.isCaseSensitive(),
                "IOCase.SENSITIVE should always report case-sensitive comparison");

        assertFalse(IOCase.INSENSITIVE.isCaseSensitive(),
                "IOCase.INSENSITIVE should always report case-insensitive comparison");

        assertEquals(IS_CASE_SENSITIVE_OS, IOCase.SYSTEM.isCaseSensitive(),
                "IOCase.SYSTEM should reflect the case-sensitivity of the current operating system");
    }
}
