package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkStartsWith_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    @DisplayName("checkStartsWith respects case sensitivity for SENSITIVE, INSENSITIVE, and SYSTEM modes")
    void test_checkStartsWith_case() {
        // SENSITIVE mode always performs an exact case comparison
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"),
                "SENSITIVE: 'ABC' starts with 'AB' (matching case) should be true");
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "Ab"),
                "SENSITIVE: 'ABC' starts with 'Ab' (mismatched case) should be false");

        // INSENSITIVE mode ignores case differences
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "AB"),
                "INSENSITIVE: 'ABC' starts with 'AB' (matching case) should be true");
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "Ab"),
                "INSENSITIVE: 'ABC' starts with 'Ab' (mismatched case) should be true");

        // SYSTEM mode follows OS rules: case-insensitive on Windows, case-sensitive on Unix
        assertTrue(IOCase.SYSTEM.checkStartsWith("ABC", "AB"),
                "SYSTEM: 'ABC' starts with 'AB' (matching case) should be true on all platforms");
        assertEquals(WINDOWS, IOCase.SYSTEM.checkStartsWith("ABC", "Ab"),
                "SYSTEM: 'ABC' starts with 'Ab' (mismatched case) should match only on Windows");
    }
}
