package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that day 29 of December (the first day of the leap week) is rejected
 * for years that are not leap years in the Symmetry454 calendar.
 *
 * In Symmetry454, December normally has 28 days; only in leap years does it
 * extend to 35 days via the leap week.  Creating a date with day-of-month 29
 * in December of a non-leap year must therefore throw DateTimeException.
 */
@SuppressWarnings({"static-method"})
public class TestSymmetry454Chronology_test_badLeapDayDates {

    /**
     * Years that are NOT leap years in the Symmetry454 calendar.
     * The leap formula is: isLeap(y) iff 52 > ((52 * y + 146) % 293).
     * None of these years satisfy that condition.
     */
    public static Object[][] data_nonLeapYears() {
        return new Object[][] {
            {    1 },
            {  100 },
            {  200 },
            { 2000 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_nonLeapYears")
    public void test_badLeapDayDates(int year) {
        // Day 29 of December belongs to the leap week, which does not exist in non-leap years.
        assertThrows(DateTimeException.class, () -> Symmetry454Date.of(year, 12, 29));
    }
}
