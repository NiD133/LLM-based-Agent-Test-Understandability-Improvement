package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkCompare_functionality {

    @Test
    void test_checkCompare_functionality() {
        // Non-empty string is greater than empty string
        assertTrue(IOCase.SENSITIVE.checkCompareTo("ABC", "") > 0);
        // Empty string is less than non-empty string
        assertTrue(IOCase.SENSITIVE.checkCompareTo("", "ABC") < 0);

        // Lexicographically earlier string is less than later string
        assertTrue(IOCase.SENSITIVE.checkCompareTo("ABC", "DEF") < 0);
        // Lexicographically later string is greater than earlier string
        assertTrue(IOCase.SENSITIVE.checkCompareTo("DEF", "ABC") > 0);

        // Identical strings compare as equal
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo("ABC", "ABC"));
        // Two empty strings compare as equal
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo("", ""));

        // Null arguments must throw NullPointerException
        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo("ABC", null));
        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo(null, "ABC"));
        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo(null, null));
    }
}
