package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkCompare_case {

    // true on Windows (case-insensitive file system), false on Unix
    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkCompare_case() {
        // SENSITIVE: comparisons are always case-sensitive
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo("ABC", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkCompareTo("ABC", "abc") < 0); // 'A' < 'a' in Unicode
        assertTrue(IOCase.SENSITIVE.checkCompareTo("abc", "ABC") > 0); // 'a' > 'A' in Unicode

        // INSENSITIVE: comparisons ignore case entirely
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "ABC"));
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "abc"));
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("abc", "ABC"));

        // SYSTEM: follows the host OS convention — case-insensitive on Windows, case-sensitive on Unix
        assertEquals(0, IOCase.SYSTEM.checkCompareTo("ABC", "ABC")); // equal strings always compare as 0
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo("ABC", "abc") == 0); // 0 only on Windows
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo("abc", "ABC") == 0); // 0 only on Windows
    }
}
