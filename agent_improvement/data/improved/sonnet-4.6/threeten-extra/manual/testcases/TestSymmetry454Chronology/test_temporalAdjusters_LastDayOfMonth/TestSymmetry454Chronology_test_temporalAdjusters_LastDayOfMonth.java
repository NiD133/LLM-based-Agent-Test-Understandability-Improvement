package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_temporalAdjusters_LastDayOfMonth {

    /**
     * Test data for {@link TemporalAdjusters#lastDayOfMonth()} applied to Symmetry454 dates.
     *
     * <p>In the Symmetry454 calendar, each month has either 28 or 35 days:
     * <ul>
     *   <li>28-day months: January, March, April, June, July, September, October, December (non-leap years)</li>
     *   <li>35-day months: February, May, August, November, and December in leap years</li>
     * </ul>
     *
     * <p>Columns: year, month, day (input), expectedYear, expectedMonth, expectedDay (last day of that month)
     */
    public static Object[][] data_temporalAdjusters_lastDayOfMonth() {
        return new Object[][] {
            // Non-leap year 2012: 28-day months → last day is 28; 35-day months → last day is 35
            { 2012,  1, 23,  2012,  1, 28 },  // January  (28 days)
            { 2012,  2, 23,  2012,  2, 35 },  // February (35 days)
            { 2012,  3, 23,  2012,  3, 28 },  // March    (28 days)
            { 2012,  4, 23,  2012,  4, 28 },  // April    (28 days)
            { 2012,  5, 23,  2012,  5, 35 },  // May      (35 days)
            { 2012,  6, 23,  2012,  6, 28 },  // June     (28 days)
            { 2012,  7, 23,  2012,  7, 28 },  // July     (28 days)
            { 2012,  8, 23,  2012,  8, 35 },  // August   (35 days)
            { 2012,  9, 23,  2012,  9, 28 },  // September(28 days)
            { 2012, 10, 23,  2012, 10, 28 },  // October  (28 days)
            { 2012, 11, 23,  2012, 11, 35 },  // November (35 days)
            { 2012, 12, 23,  2012, 12, 28 },  // December (28 days, non-leap)

            // Leap year 2009: December gains the extra leap week → 35 days
            { 2009, 12, 23,  2009, 12, 35 },  // December (35 days, leap year)
        };
    }

    @ParameterizedTest
    @MethodSource("data_temporalAdjusters_lastDayOfMonth")
    public void test_temporalAdjusters_LastDayOfMonth(
            int year, int month, int day,
            int expectedYear, int expectedMonth, int expectedDay) {
        Symmetry454Date base = Symmetry454Date.of(year, month, day);
        Symmetry454Date expected = Symmetry454Date.of(expectedYear, expectedMonth, expectedDay);
        Symmetry454Date actual = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(expected, actual);
    }
}
