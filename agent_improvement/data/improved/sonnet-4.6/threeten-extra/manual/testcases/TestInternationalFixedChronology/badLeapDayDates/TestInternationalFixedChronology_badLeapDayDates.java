package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that day 29 of month 6 (Leap Day) is rejected in non-leap years.
 *
 * The International Fixed Calendar inserts a Leap Day as month 6, day 29,
 * following the same leap-year rules as the Gregorian calendar (divisible by 4,
 * except centuries unless also divisible by 400). Years 1, 100, 200, 300, and
 * 1900 are all non-leap years, so constructing month-6 day-29 must throw.
 */
@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_badLeapDayDates {

    /**
     * Non-leap years in which month 6, day 29 (Leap Day) must not exist.
     * Covers: a regular non-leap year (1), century years that are not
     * divisible by 400 (100, 200, 300, 1900).
     */
    public static Object[][] data_badLeapDates() {
        return new Object[][] {
            { 1 },
            { 100 },
            { 200 },
            { 300 },
            { 1900 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_badLeapDates")
    public void badLeapDayDates(int nonLeapYear) {
        assertThrows(DateTimeException.class,
                () -> InternationalFixedDate.of(nonLeapYear, 6, 29));
    }
}
