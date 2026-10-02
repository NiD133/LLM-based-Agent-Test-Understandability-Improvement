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
 * Tests for {@link Symmetry454Date#plus(long, TemporalUnit)} when the starting date falls
 * within or adjacent to a leap week. Year 2015 is a leap year in the Symmetry454 calendar,
 * giving December 35 days instead of the usual 28.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_plus_leapWeek_TemporalUnit {

    /**
     * Provides (startYear, startMonth, startDom, amount, unit, expectedYear, expectedMonth, expectedDom).
     * The start date is always 2015-12-28 — the last day of the regular portion of December in
     * a leap year (the leap week occupies days 29-35 of that month).
     */
    public static Object[][] data_plus_leapWeek() {
        return new Object[][] {
            // --- DAYS ---
            // Adding zero days: no change
            { 2015, 12, 28,  0, DAYS,   2015, 12, 28 },
            // Adding 8 days crosses the 7-day leap week and lands in year 2016
            { 2015, 12, 28,  8, DAYS,   2016,  1,  1 },
            // Subtracting days stays within the same month
            { 2015, 12, 28, -3, DAYS,   2015, 12, 25 },

            // --- WEEKS ---
            // Adding zero weeks: no change
            { 2015, 12, 28,  0, WEEKS,  2015, 12, 28 },
            // Adding weeks crosses the year boundary
            { 2015, 12, 28,  3, WEEKS,  2016,  1, 14 },
            // Subtracting weeks moves back into a previous month
            { 2015, 12, 28, -5, WEEKS,  2015, 11, 28 },
            // Adding 52 weeks (one normal year) from a leap year skips the leap week
            { 2015, 12, 28, 52, WEEKS,  2016, 12, 21 },

            // --- MONTHS ---
            // Adding zero months: no change
            { 2015, 12, 28,  0, MONTHS, 2015, 12, 28 },
            // Adding months within the next year
            { 2015, 12, 28,  3, MONTHS, 2016,  3, 28 },
            // Subtracting months stays within 2015
            { 2015, 12, 28, -5, MONTHS, 2015,  7, 28 },
            // Adding 12 months lands on the same day-of-month in the next year
            { 2015, 12, 28, 12, MONTHS, 2016, 12, 28 },

            // --- YEARS ---
            // Adding zero years: no change
            { 2015, 12, 28,  0, YEARS,  2015, 12, 28 },
            // Adding years preserves month and day-of-month
            { 2015, 12, 28,  3, YEARS,  2018, 12, 28 },
            // Subtracting years preserves month and day-of-month
            { 2015, 12, 28, -5, YEARS,  2010, 12, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leapWeek")
    public void test_plus_leapWeek_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        Symmetry454Date start    = Symmetry454Date.of(year, month, dom);
        Symmetry454Date expected = Symmetry454Date.of(expectedYear, expectedMonth, expectedDom);
        assertEquals(expected, start.plus(amount, unit));
    }
}
