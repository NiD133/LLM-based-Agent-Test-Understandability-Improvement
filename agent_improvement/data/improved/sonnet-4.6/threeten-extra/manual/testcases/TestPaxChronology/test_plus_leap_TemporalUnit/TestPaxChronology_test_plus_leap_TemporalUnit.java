package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plus_leap_TemporalUnit {

    /**
     * Test cases where the result lands in (or near) a leap month (month 13 in a leap year).
     * In Pax leap years, month 13 is a short 7-day "Pax" month inserted before the regular month 13
     * (which becomes month 14). Day values that exceed the month length are clamped.
     *
     * Columns: startYear, startMonth, startDay, amount, unit,
     *          expectedYear, expectedMonth, expectedDay
     */
    public static Object[][] data_plus_leap() {
        return new Object[][] {
            // Adding 1 month from month 12 lands in leap month 13 (only 7 days); day 26 clamps to 7
            { 2012, 12, 26,  1, MONTHS, 2012, 13, 7 },
            // Subtracting 1 month from month 14 (post-leap) also lands in leap month 13; day 26 clamps to 7
            { 2012, 14, 26, -1, MONTHS, 2012, 13, 7 },
            // Adding 3 years from leap month 13 stays in a non-leap year's month 13 (day preserved)
            { 2012, 13,  6,  3, YEARS,  2015, 13, 6 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leap")
    public void test_plus_leap_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
            PaxDate.of(expectedYear, expectedMonth, expectedDom),
            PaxDate.of(year, month, dom).plus(amount, unit));
    }
}
