package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkRegionMatches_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkRegionMatches_case() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "AB"));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "Ab"));

        assertTrue(IOCase.INSENSITIVE.checkRegionMatches("ABC", 0, "AB"));
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches("ABC", 0, "Ab"));

        assertTrue(IOCase.SYSTEM.checkRegionMatches("ABC", 0, "AB"));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkRegionMatches("ABC", 0, "Ab"));
    }
}
