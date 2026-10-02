package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkCompare_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Test
    void test_checkCompare_case() {
        assertSensitiveComparisonUsesCharacterCase();
        assertInsensitiveComparisonIgnoresCharacterCase();
        assertSystemComparisonFollowsOperatingSystemCaseRules();
    }

    private void assertSensitiveComparisonUsesCharacterCase() {
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo("ABC", "ABC"));
        assertTrue(IOCase.SENSITIVE.checkCompareTo("ABC", "abc") < 0);
        assertTrue(IOCase.SENSITIVE.checkCompareTo("abc", "ABC") > 0);
    }

    private void assertInsensitiveComparisonIgnoresCharacterCase() {
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "ABC"));
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "abc"));
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("abc", "ABC"));
    }

    private void assertSystemComparisonFollowsOperatingSystemCaseRules() {
        assertEquals(0, IOCase.SYSTEM.checkCompareTo("ABC", "ABC"));
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo("ABC", "abc") == 0);
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo("abc", "ABC") == 0);
    }
}
