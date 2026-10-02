package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkIndexOf_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkIndexOf_case() {
        assertSensitiveSearches();
        assertInsensitiveSearches();
        assertSystemSearches();
    }

    private void assertSensitiveSearches() {
        assertEquals(1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, "BC"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, "Bc"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, "Bc"));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, null));
    }

    private void assertInsensitiveSearches() {
        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "BC"));
        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "Bc"));
    }

    private void assertSystemSearches() {
        assertEquals(1, IOCase.SYSTEM.checkIndexOf("ABC", 0, "BC"));
        assertEquals(WINDOWS ? 1 : -1, IOCase.SYSTEM.checkIndexOf("ABC", 0, "Bc"));
    }
}
