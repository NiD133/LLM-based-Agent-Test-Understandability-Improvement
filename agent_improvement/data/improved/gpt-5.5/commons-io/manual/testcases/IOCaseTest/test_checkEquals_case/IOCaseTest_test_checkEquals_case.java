package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkEquals_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';
    private static final String UPPER_CASE_TEXT = "ABC";
    private static final String MIXED_CASE_TEXT = "Abc";

    @Test
    void test_checkEquals_case() {
        assertTrue(IOCase.SENSITIVE.checkEquals(UPPER_CASE_TEXT, UPPER_CASE_TEXT));
        assertFalse(IOCase.SENSITIVE.checkEquals(UPPER_CASE_TEXT, MIXED_CASE_TEXT));

        assertTrue(IOCase.INSENSITIVE.checkEquals(UPPER_CASE_TEXT, UPPER_CASE_TEXT));
        assertTrue(IOCase.INSENSITIVE.checkEquals(UPPER_CASE_TEXT, MIXED_CASE_TEXT));

        assertTrue(IOCase.SYSTEM.checkEquals(UPPER_CASE_TEXT, UPPER_CASE_TEXT));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEquals(UPPER_CASE_TEXT, MIXED_CASE_TEXT));
    }
}
