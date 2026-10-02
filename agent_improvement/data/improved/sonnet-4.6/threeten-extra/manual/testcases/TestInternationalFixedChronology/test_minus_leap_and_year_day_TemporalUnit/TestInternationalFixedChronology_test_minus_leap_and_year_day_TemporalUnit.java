package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_minus_leap_and_year_day_TemporalUnit {

    /**
     * Test data for {@link #test_minus_leap_and_year_day_TemporalUnit}.
     *
     * Each row: { expectedYear, expectedMonth, expectedDom, amount, unit, year, month, dom }
     * Assertion: InternationalFixedDate.of(year, month, dom).minus(amount, unit)
     *            == InternationalFixedDate.of(expectedYear, expectedMonth, expectedDom)
     *
     * The test covers two special dates that do not belong to a regular 28-day week:
     *   - Year Day:  month 13, day 29  (every year)
     *   - Leap Day:  month  6, day 29  (leap years only, e.g. 2012)
     */
    public static Object[][] data_minus_leap_and_year_day() {
        return new Object[][] {
            // --- Year Day (2014/13/29) as the starting point ---

            // DAYS arithmetic
            { 2014, 13, 29,  0, DAYS,   2014, 13, 29 },  // minus 0 days: unchanged
            { 2014, 13, 21,  8, DAYS,   2014, 13, 29 },  // minus 8 days: stays within month 13
            { 2015,  1,  3, -3, DAYS,   2014, 13, 29 },  // minus -3 days (i.e. +3): crosses into next year

            // WEEKS arithmetic
            { 2014, 13, 29,  0, WEEKS,  2014, 13, 29 },  // minus 0 weeks: unchanged
            { 2014, 13,  7,  3, WEEKS,  2014, 13, 29 },  // minus 3 weeks: stays within month 13
            { 2015,  2,  7, -5, WEEKS,  2014, 13, 29 },  // minus -5 weeks (i.e. +5): crosses into next year
            { 2013, 13, 29, 52, WEEKS,  2014, 13, 29 },  // minus 52 weeks: exactly one year back to Year Day

            // MONTHS arithmetic
            { 2014, 13, 29,  0, MONTHS, 2014, 13, 29 },  // minus 0 months: unchanged
            { 2014, 10, 28,  3, MONTHS, 2014, 13, 29 },  // minus 3 months: result clamped to 28 (no day 29 in month 10)
            { 2015,  5, 28, -5, MONTHS, 2014, 13, 29 },  // minus -5 months (i.e. +5): into next year, clamped
            { 2013, 13, 29, 13, MONTHS, 2014, 13, 29 },  // minus 13 months: one full year back, lands on Year Day

            // YEARS arithmetic
            { 2014, 13, 29,  0, YEARS,  2014, 13, 29 },  // minus 0 years: unchanged
            { 2011, 13, 29,  3, YEARS,  2014, 13, 29 },  // minus 3 years: Year Day is always valid
            { 2019, 13, 29, -5, YEARS,  2014, 13, 29 },  // minus -5 years (i.e. +5): Year Day still valid

            // --- Cross-special-day: Year Day <-> Leap Day ---
            { 2012,  6, 29, 4 * -6, WEEKS, 2011, 13, 29 }, // 2011 Year Day minus -24 weeks → 2012 Leap Day
            { 2012,  6, 29,  4 * 7, WEEKS, 2012, 13, 29 }, // 2012 Year Day minus 28 weeks → 2012 Leap Day

            // --- Leap Day (2012/6/29) as the starting point ---

            // DAYS arithmetic
            { 2012,  6, 29,  0, DAYS,   2012,  6, 29 },  // minus 0 days: unchanged
            { 2012,  6, 21,  8, DAYS,   2012,  6, 29 },  // minus 8 days: stays within month 6
            { 2012,  7,  3, -3, DAYS,   2012,  6, 29 },  // minus -3 days (i.e. +3): crosses into month 7

            // WEEKS arithmetic
            { 2012,  6, 29,  0, WEEKS,  2012,  6, 29 },  // minus 0 weeks: unchanged
            { 2012,  6,  8,  3, WEEKS,  2012,  6, 29 },  // minus 3 weeks: stays within month 6
            { 2012,  8,  8, -5, WEEKS,  2012,  6, 29 },  // minus -5 weeks (i.e. +5): crosses into month 8
            { 2012,  6, 29, 28, WEEKS,  2012, 13, 29 },  // 2012 Year Day minus 28 weeks → 2012 Leap Day
            { 2008,  6, 29, 52 * 4, WEEKS, 2012, 6, 29 }, // minus 4*52 weeks: back to Leap Day in 2008

            // MONTHS arithmetic
            { 2012,  6, 29,  0, MONTHS, 2012,  6, 29 },  // minus 0 months: unchanged
            { 2012,  3, 28,  3, MONTHS, 2012,  6, 29 },  // minus 3 months: month 3 has no day 29, clamped to 28
            { 2012, 11, 28, -5, MONTHS, 2012,  6, 29 },  // minus -5 months (i.e. +5): month 11 clamped to 28
            { 2008,  6, 29, 13 * 4, MONTHS, 2012, 6, 29 }, // minus 4*13 months: 4 years back to Leap Day

            // YEARS arithmetic
            { 2012,  6, 29,  0, YEARS,  2012,  6, 29 },  // minus 0 years: unchanged
            { 2009,  6, 28,  3, YEARS,  2012,  6, 29 },  // minus 3 years: 2009 is not a leap year, day 29 clamped to 28
            { 2017,  6, 28, -5, YEARS,  2012,  6, 29 },  // minus -5 years (i.e. +5): 2017 not a leap year, clamped to 28
            { 2008,  6, 29,  4, YEARS,  2012,  6, 29 },  // minus 4 years: 2008 is a leap year, day 29 preserved

            // --- Cross-special-day: Leap Day <-> Year Day ---
            { 2012, 13, 29, 4 * -7, WEEKS, 2012,  6, 29 }, // 2012 Leap Day minus -28 weeks → 2012 Year Day
            { 2011, 13, 29,  4 * 6, WEEKS, 2012,  6, 29 }, // 2012 Leap Day minus 24 weeks → 2011 Year Day
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leap_and_year_day")
    public void test_minus_leap_and_year_day_TemporalUnit(
            int expectedYear, int expectedMonth, int expectedDom,
            long amount, TemporalUnit unit,
            int year, int month, int dom) {
        assertEquals(
                InternationalFixedDate.of(expectedYear, expectedMonth, expectedDom),
                InternationalFixedDate.of(year, month, dom).minus(amount, unit));
    }
}
