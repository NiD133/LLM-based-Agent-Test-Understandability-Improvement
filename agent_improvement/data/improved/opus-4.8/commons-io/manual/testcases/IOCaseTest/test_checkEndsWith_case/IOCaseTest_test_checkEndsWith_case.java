package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#checkEndsWith(String, String)}, which checks whether a
 * string ends with a given suffix while honouring the case-sensitivity rule of
 * the {@link IOCase} constant.
 */
public class IOCaseTest_test_checkEndsWith_case {

    /** True when running on a (case-insensitive) Windows file system. */
    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkEndsWith_case() {
        // SENSITIVE: the suffix must match exactly, including letter case.
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "BC"),
                "exact-case suffix should match under SENSITIVE");
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "Bc"),
                "differently-cased suffix should not match under SENSITIVE");

        // INSENSITIVE: letter case is ignored, so both suffixes match.
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "BC"),
                "exact-case suffix should match under INSENSITIVE");
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "Bc"),
                "differently-cased suffix should still match under INSENSITIVE");

        // SYSTEM: behaviour depends on the host file system.
        // An exact-case suffix matches everywhere.
        assertTrue(IOCase.SYSTEM.checkEndsWith("ABC", "BC"),
                "exact-case suffix should match under SYSTEM");
        // A differently-cased suffix only matches on case-insensitive Windows.
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEndsWith("ABC", "Bc"),
                "differently-cased suffix should match under SYSTEM only on Windows");
    }
}
