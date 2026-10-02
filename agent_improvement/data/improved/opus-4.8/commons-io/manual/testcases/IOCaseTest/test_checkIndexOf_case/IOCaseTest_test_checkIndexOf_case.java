package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#checkIndexOf(String, int, String)} for each
 * case-sensitivity mode (SENSITIVE, INSENSITIVE and the OS-dependent SYSTEM).
 */
public class IOCaseTest_test_checkIndexOf_case {

    /** True when the current OS uses the Windows file separator (case-insensitive file names). */
    private static final boolean WINDOWS = File.separatorChar == '\\';

    /** Index that {@code checkIndexOf} should return when the search string is not found. */
    private static final int NOT_FOUND = -1;

    @Test
    void test_checkIndexOf_case() {
        // SENSITIVE: an exact-case match is required.
        assertEquals(1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, "BC"), "exact-case substring found at index 1");
        assertEquals(NOT_FOUND, IOCase.SENSITIVE.checkIndexOf("ABC", 0, "Bc"), "case mismatch is not a match");

        // SENSITIVE: a null string or null search always yields NOT_FOUND.
        assertEquals(NOT_FOUND, IOCase.SENSITIVE.checkIndexOf(null, 0, "Bc"), "null input string");
        assertEquals(NOT_FOUND, IOCase.SENSITIVE.checkIndexOf(null, 0, null), "null input and search");
        assertEquals(NOT_FOUND, IOCase.SENSITIVE.checkIndexOf("ABC", 0, null), "null search string");

        // INSENSITIVE: case differences are ignored, so both find the substring at index 1.
        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "BC"), "exact-case substring found at index 1");
        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "Bc"), "case-differing substring still found at index 1");

        // SYSTEM: behaves like INSENSITIVE on Windows and like SENSITIVE on Unix.
        assertEquals(1, IOCase.SYSTEM.checkIndexOf("ABC", 0, "BC"), "exact-case substring found at index 1");
        assertEquals(WINDOWS ? 1 : NOT_FOUND, IOCase.SYSTEM.checkIndexOf("ABC", 0, "Bc"),
                "case-differing substring matches only on case-insensitive (Windows) systems");
    }
}
