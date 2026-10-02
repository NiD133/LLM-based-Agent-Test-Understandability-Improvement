package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkEquals_case {

    // true when running on Windows, where the file system is case-insensitive
    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkEquals_case() {
        // SENSITIVE always performs exact, case-sensitive comparison
        assertTrue(IOCase.SENSITIVE.checkEquals("ABC", "ABC"));
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "Abc"));

        // INSENSITIVE always ignores case differences
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "ABC"));
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "Abc"));

        // SYSTEM matches the host OS: case-sensitive on Unix, case-insensitive on Windows
        assertTrue(IOCase.SYSTEM.checkEquals("ABC", "ABC"));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEquals("ABC", "Abc"));
    }
}
