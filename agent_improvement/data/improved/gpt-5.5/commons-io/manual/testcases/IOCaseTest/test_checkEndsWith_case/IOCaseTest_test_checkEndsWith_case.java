package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkEndsWith_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';
    private static final String VALUE = "ABC";
    private static final String MATCHING_SUFFIX = "BC";
    private static final String DIFFERENT_CASE_SUFFIX = "Bc";

    @Test
    void test_checkEndsWith_case() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith(VALUE, MATCHING_SUFFIX));
        assertFalse(IOCase.SENSITIVE.checkEndsWith(VALUE, DIFFERENT_CASE_SUFFIX));

        assertTrue(IOCase.INSENSITIVE.checkEndsWith(VALUE, MATCHING_SUFFIX));
        assertTrue(IOCase.INSENSITIVE.checkEndsWith(VALUE, DIFFERENT_CASE_SUFFIX));

        assertTrue(IOCase.SYSTEM.checkEndsWith(VALUE, MATCHING_SUFFIX));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEndsWith(VALUE, DIFFERENT_CASE_SUFFIX));
    }
}
