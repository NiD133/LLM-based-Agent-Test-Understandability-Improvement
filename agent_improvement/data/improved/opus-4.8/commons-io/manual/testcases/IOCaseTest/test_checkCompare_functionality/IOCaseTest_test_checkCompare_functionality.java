package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link IOCase#checkCompareTo(String, String)} for the case-sensitive mode.
 *
 * <p>In SENSITIVE mode the method behaves like {@link String#compareTo(String)}:
 * it returns a negative number, zero, or a positive number depending on the
 * ordering of the two arguments, and rejects {@code null} arguments.</p>
 */
public class IOCaseTest_test_checkCompare_functionality {

    /** The case-sensitive comparator under test. */
    private final IOCase sensitive = IOCase.SENSITIVE;

    @Test
    void checkCompareTo_returnsPositive_whenFirstStringOrdersAfterSecond() {
        // A non-empty string orders after the empty string.
        assertTrue(sensitive.checkCompareTo("ABC", "") > 0);
        // "DEF" orders after "ABC".
        assertTrue(sensitive.checkCompareTo("DEF", "ABC") > 0);
    }

    @Test
    void checkCompareTo_returnsNegative_whenFirstStringOrdersBeforeSecond() {
        // The empty string orders before a non-empty string.
        assertTrue(sensitive.checkCompareTo("", "ABC") < 0);
        // "ABC" orders before "DEF".
        assertTrue(sensitive.checkCompareTo("ABC", "DEF") < 0);
    }

    @Test
    void checkCompareTo_returnsZero_whenStringsAreEqual() {
        assertEquals(0, sensitive.checkCompareTo("ABC", "ABC"));
        assertEquals(0, sensitive.checkCompareTo("", ""));
    }

    @Test
    void checkCompareTo_throwsNullPointerException_whenEitherArgumentIsNull() {
        assertThrows(NullPointerException.class, () -> sensitive.checkCompareTo("ABC", null));
        assertThrows(NullPointerException.class, () -> sensitive.checkCompareTo(null, "ABC"));
        assertThrows(NullPointerException.class, () -> sensitive.checkCompareTo(null, null));
    }
}
