package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#checkCompareTo(String, String)} for each case-sensitivity mode.
 * <p>
 * {@code checkCompareTo} mirrors {@link String#compareTo} but applies the
 * case-sensitivity rule of the {@link IOCase} constant: it returns 0 when the
 * strings are considered equal, a negative value when the first sorts before the
 * second, and a positive value otherwise.
 * </p>
 */
public class IOCaseTest_test_checkCompare_case {

    /** True when running on a Windows-style (case-insensitive) file system. */
    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkCompare_case() {
        // SENSITIVE: case matters, so "ABC" and "abc" are ordered like String.compareTo.
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo("ABC", "ABC"), "identical strings are equal");
        assertTrue(IOCase.SENSITIVE.checkCompareTo("ABC", "abc") < 0, "uppercase sorts before lowercase");
        assertTrue(IOCase.SENSITIVE.checkCompareTo("abc", "ABC") > 0, "lowercase sorts after uppercase");

        // INSENSITIVE: case is ignored, so all three comparisons treat the strings as equal.
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "ABC"), "identical strings are equal");
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "abc"), "case differences are ignored");
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("abc", "ABC"), "case differences are ignored");

        // SYSTEM: behaves like INSENSITIVE on Windows and like SENSITIVE elsewhere.
        assertEquals(0, IOCase.SYSTEM.checkCompareTo("ABC", "ABC"), "identical strings are equal");
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo("ABC", "abc") == 0,
                "case is ignored only on Windows");
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo("abc", "ABC") == 0,
                "case is ignored only on Windows");
    }
}
