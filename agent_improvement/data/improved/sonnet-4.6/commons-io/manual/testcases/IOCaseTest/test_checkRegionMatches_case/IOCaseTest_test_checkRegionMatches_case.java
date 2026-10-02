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
        final String str = "ABC";
        final int offset = 0;
        final String exactMatch = "AB";
        final String mixedCaseSearch = "Ab";

        // SENSITIVE: exact case match succeeds, different case fails
        assertTrue(IOCase.SENSITIVE.checkRegionMatches(str, offset, exactMatch));
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(str, offset, mixedCaseSearch));

        // INSENSITIVE: both exact and mixed-case searches succeed
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches(str, offset, exactMatch));
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches(str, offset, mixedCaseSearch));

        // SYSTEM: exact case always matches; mixed case depends on the OS
        // (case-insensitive on Windows, case-sensitive on Unix)
        assertTrue(IOCase.SYSTEM.checkRegionMatches(str, offset, exactMatch));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkRegionMatches(str, offset, mixedCaseSearch));
    }
}
