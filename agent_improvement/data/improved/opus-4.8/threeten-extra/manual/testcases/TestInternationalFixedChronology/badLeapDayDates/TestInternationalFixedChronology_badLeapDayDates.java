package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that the Leap Day (month 6, day 29) cannot be created in a non-leap year.
 * <p>
 * The International Fixed calendar shares the Gregorian leap-year rule, so a year
 * is a leap year only when it is divisible by 4 but not by 100, unless it is also
 * divisible by 400. Each year below is therefore a common (non-leap) year, and
 * asking for its Leap Day must be rejected.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_badLeapDayDates {

    /** The Leap Day lives at month 6, day 29 in a leap year. */
    private static final int LEAP_MONTH = 6;
    private static final int LEAP_DAY = 29;

    /** Years that are NOT leap years, so they have no Leap Day. */
    public static Object[][] data_nonLeapYears() {
        return new Object[][] {
            { 1 },      // not divisible by 4
            { 100 },    // divisible by 100 but not 400
            { 200 },    // divisible by 100 but not 400
            { 300 },    // divisible by 100 but not 400
            { 1900 },   // divisible by 100 but not 400
        };
    }

    @ParameterizedTest
    @MethodSource("data_nonLeapYears")
    public void of_leapDayInNonLeapYear_throws(int year) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, LEAP_MONTH, LEAP_DAY));
    }
}
