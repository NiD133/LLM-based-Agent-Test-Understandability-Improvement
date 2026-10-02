package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry454Date#lengthOfMonth()} returns the correct number of days
 * for the first day of each month in the Symmetry454 calendar.
 *
 * <p>In the Symmetry454 calendar:
 * <ul>
 *   <li>Short months (months 1, 3, 4, 6, 7, 9, 10, 12 in non-leap years) have 28 days.</li>
 *   <li>Long months (months 2, 5, 8, 11 — the middle month of each quarter) have 35 days.</li>
 *   <li>Month 12 in a leap year also has 35 days (the extra leap week is appended to December).</li>
 * </ul>
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_lengthOfMonthFirst {

    /**
     * Provides test data as {year, month, day, expectedLengthOfMonth}.
     * Note: the {@code day} column is not used by the test — the date is always
     * constructed with day=1. It is kept to match the original data shape.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // year   month  day  expectedDays
            { 2000,   1,    28,  28 },  // Jan: short month (4 weeks)
            { 2000,   2,    28,  35 },  // Feb: long month  (5 weeks, middle of Q1)
            { 2000,   3,    28,  28 },  // Mar: short month
            { 2000,   4,    28,  28 },  // Apr: short month
            { 2000,   5,    28,  35 },  // May: long month  (middle of Q2)
            { 2000,   6,    28,  28 },  // Jun: short month
            { 2000,   7,    28,  28 },  // Jul: short month
            { 2000,   8,    28,  35 },  // Aug: long month  (middle of Q3)
            { 2000,   9,    28,  28 },  // Sep: short month
            { 2000,  10,    28,  28 },  // Oct: short month
            { 2000,  11,    28,  35 },  // Nov: long month  (middle of Q4)
            { 2000,  12,    28,  28 },  // Dec: short month (non-leap year 2000)
            { 2004,  12,    20,  35 },  // Dec: long month  (leap year 2004 — extra week added to Dec)
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonthFirst(int year, int month, int day, int length) {
        assertEquals(length, Symmetry454Date.of(year, month, 1).lengthOfMonth());
    }
}
