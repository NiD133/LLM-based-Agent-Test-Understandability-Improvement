package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkStartsWith_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkStartsWith_case() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"));
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "Ab"));

        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "AB"));
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "Ab"));

        assertTrue(IOCase.SYSTEM.checkStartsWith("ABC", "AB"));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkStartsWith("ABC", "Ab"));
    }
}
