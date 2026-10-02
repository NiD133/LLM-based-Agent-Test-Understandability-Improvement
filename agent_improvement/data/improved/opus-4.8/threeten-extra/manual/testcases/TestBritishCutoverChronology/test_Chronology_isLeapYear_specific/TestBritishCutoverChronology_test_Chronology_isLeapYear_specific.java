package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link BritishCutoverChronology#isLeapYear(long)} for a handful of
 * specific proleptic years.
 * <p>
 * The British cutover chronology follows the Julian leap-year rule for these
 * years: a year is a leap year exactly when it is divisible by 4 (including
 * the proleptic year 0 and negative years). The cases below cover a contiguous
 * run of years around 0 so the every-fourth-year pattern is easy to see.
 */
public class TestBritishCutoverChronology_test_Chronology_isLeapYear_specific {

    private static final BritishCutoverChronology CHRONO = BritishCutoverChronology.INSTANCE;

    /**
     * Asserts that {@code year} is reported as a leap year iff it is divisible by 4.
     */
    private static void assertLeapYearMatchesDivisibleBy4(long year) {
        boolean expectedLeap = (year % 4 == 0);
        assertEquals(expectedLeap, CHRONO.isLeapYear(year),
                "isLeapYear(" + year + ") should be " + expectedLeap);
    }

    @Test
    public void test_Chronology_isLeapYear_specific() {
        // Leap years (divisible by 4): 8, 4, 0, -4
        // Non-leap years: everything else in between.
        for (long year = 8; year >= -6; year--) {
            assertLeapYearMatchesDivisibleBy4(year);
        }
    }
}
