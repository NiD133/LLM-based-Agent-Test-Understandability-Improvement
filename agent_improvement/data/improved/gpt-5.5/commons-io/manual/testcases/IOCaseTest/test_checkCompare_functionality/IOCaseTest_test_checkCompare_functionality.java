package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkCompare_functionality {

    @Test
    void test_checkCompare_functionality() {
        assertFirstStringSortsAfterSecond("ABC", "");
        assertFirstStringSortsBeforeSecond("", "ABC");
        assertFirstStringSortsBeforeSecond("ABC", "DEF");
        assertFirstStringSortsAfterSecond("DEF", "ABC");

        assertStringsCompareAsEqual("ABC", "ABC");
        assertStringsCompareAsEqual("", "");

        assertNullInputRejected("ABC", null);
        assertNullInputRejected(null, "ABC");
        assertNullInputRejected(null, null);
    }

    private static void assertFirstStringSortsAfterSecond(final String first, final String second) {
        assertTrue(IOCase.SENSITIVE.checkCompareTo(first, second) > 0);
    }

    private static void assertFirstStringSortsBeforeSecond(final String first, final String second) {
        assertTrue(IOCase.SENSITIVE.checkCompareTo(first, second) < 0);
    }

    private static void assertStringsCompareAsEqual(final String first, final String second) {
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo(first, second));
    }

    private static void assertNullInputRejected(final String first, final String second) {
        assertThrows(NullPointerException.class, () -> IOCase.SENSITIVE.checkCompareTo(first, second));
    }
}
