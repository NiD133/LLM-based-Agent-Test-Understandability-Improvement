package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry010Date#plus(long, TemporalUnit)} for dates in and around the leap week.
 *
 * <p>The Symmetry010 calendar appends a 7-day leap week at the end of December in leap years,
 * giving December 37 days (days 31–37) instead of the usual 30. Year 2015 is such a leap year.
 * Day 28 of December is the last day of the regular month; days 29–37 belong to the leap week.
 */
@SuppressWarnings({"static-method"})
public class TestSymmetry010Chronology_test_plus_leapWeek_TemporalUnit {

    /**
     * Parameters: startYear, startMonth, startDom, amount, unit,
     *             expectedYear, expectedMonth, expectedDom.
     *
     * <p>All start dates are 2015-Dec-28 (last day before the leap week).
     * Each group verifies that arithmetic with a given unit correctly crosses
     * the leap-week boundary in both directions.
     */
    public static Object[][] data_plus_leapWeek() {
        return new Object[][] {
            // --- DAYS ---
            // Zero delta: no change
            { 2015, 12, 28,   0, DAYS, 2015, 12, 28 },
            // +8 days: lands inside the leap week (day 36)
            { 2015, 12, 28,   8, DAYS, 2015, 12, 36 },
            // -3 days: retreats within regular December
            { 2015, 12, 28,  -3, DAYS, 2015, 12, 25 },

            // --- WEEKS ---
            // Zero delta: no change
            { 2015, 12, 28,   0, WEEKS, 2015, 12, 28 },
            // +3 weeks: crosses into the next (non-leap) year
            { 2015, 12, 28,   3, WEEKS, 2016,  1, 12 },
            // -5 weeks: retreats into November of the same year
            { 2015, 12, 28,  -5, WEEKS, 2015, 11, 24 },
            // +52 weeks (one year): lands in non-leap December 2016
            { 2015, 12, 28,  52, WEEKS, 2016, 12, 21 },

            // --- MONTHS ---
            // Zero delta: no change
            { 2015, 12, 28,   0, MONTHS, 2015, 12, 28 },
            // +3 months: March of the following year (day 28 is valid in every month)
            { 2015, 12, 28,   3, MONTHS, 2016,  3, 28 },
            // -5 months: July of the same year
            { 2015, 12, 28,  -5, MONTHS, 2015,  7, 28 },
            // +12 months: same month/day one year later
            { 2015, 12, 28,  12, MONTHS, 2016, 12, 28 },

            // --- YEARS ---
            // Zero delta: no change
            { 2015, 12, 28,   0, YEARS, 2015, 12, 28 },
            // +3 years: day 28 is safe in all years (not a leap-week-only day)
            { 2015, 12, 28,   3, YEARS, 2018, 12, 28 },
            // -5 years
            { 2015, 12, 28,  -5, YEARS, 2010, 12, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leapWeek")
    public void test_plus_leapWeek_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        Symmetry010Date start    = Symmetry010Date.of(year, month, dom);
        Symmetry010Date expected = Symmetry010Date.of(expectedYear, expectedMonth, expectedDom);
        assertEquals(expected, start.plus(amount, unit));
    }
}
