package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry010Date#toString()} produces the expected "Sym010 CE year/MM/DD" format.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_toString {

    /**
     * Test cases: (date, expectedString)
     *
     * Format: "Sym010 CE {year}/{month 2-digit}/{day 2-digit}"
     * Month and day are zero-padded to at least 2 digits.
     * Leap-year December extends to day 37.
     */
    public static Object[][] data_toString() {
        return new Object[][] {
            // First day of the calendar epoch
            { Symmetry010Date.of(1, 1, 1),       "Sym010 CE 1/01/01" },
            // Middle month of a normal year (February has 31 days)
            { Symmetry010Date.of(1970, 2, 31),   "Sym010 CE 1970/02/31" },
            // Mid-year month with double-digit month and day
            { Symmetry010Date.of(2000, 8, 31),   "Sym010 CE 2000/08/31" },
            // Leap-year December: day 37 is the extra leap-week day
            { Symmetry010Date.of(2009, 12, 37),  "Sym010 CE 2009/12/37" },
        };
    }

    @ParameterizedTest
    @MethodSource("data_toString")
    public void test_toString(Symmetry010Date date, String expected) {
        assertEquals(expected, date.toString());
    }
}
