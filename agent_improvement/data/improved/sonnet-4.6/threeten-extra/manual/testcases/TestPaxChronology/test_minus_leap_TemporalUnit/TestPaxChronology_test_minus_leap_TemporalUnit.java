package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestPaxChronology_test_minus_leap_TemporalUnit {

    /**
     * Test cases for minus() operations involving the Pax leap month (month 13).
     *
     * Column order: expectedYear, expectedMonth, expectedDay, amount, unit, inputYear, inputMonth, inputDay
     *
     * The leap month (month 13) in a Pax leap year is only 7 days long. When arithmetic
     * crosses its boundary, dates are clamped to the last valid day of month 13.
     */
    public static Object[][] data_minus_leap() {
        return new Object[][] {
            // Subtracting a negative month from month 12 day 26 moves into month 13,
            // where the result is clamped to day 7 (the last day of the 7-day leap month).
            { 2012, 13,  7,  -1, MONTHS, 2012, 12, 26 },
            // Subtracting one month from month 14 day 26 moves back into month 13,
            // where the result is similarly clamped to day 7.
            { 2012, 13,  7,   1, MONTHS, 2012, 14, 26 },
            // Subtracting 3 years from 2015-13-6: year 2015 is non-leap (month 13 has 28 days),
            // but year 2012 is a leap year (month 13 has 7 days), so the month shifts to 14.
            { 2012, 14,  6,   3, YEARS,  2015, 13,  6 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leap")
    public void test_minus_leap_TemporalUnit(
            int expectedYear, int expectedMonth, int expectedDay,
            long amount, TemporalUnit unit,
            int inputYear, int inputMonth, int inputDay) {
        assertEquals(
            PaxDate.of(expectedYear, expectedMonth, expectedDay),
            PaxDate.of(inputYear, inputMonth, inputDay).minus(amount, unit));
    }
}
