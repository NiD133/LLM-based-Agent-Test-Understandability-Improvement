package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry010Date#lengthOfMonth()} returns the correct number of days.
 *
 * In the Symmetry010 calendar:
 * - Short months (months 1, 3, 4, 6, 7, 9, 10, 12 in normal years) have 30 days.
 * - Long months (months 2, 5, 8, 11 — the middle month of each quarter) have 31 days.
 * - December in a leap year has 37 days (the extra leap week is appended to month 12).
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_lengthOfMonth {

    /**
     * Provides (year, month, day, expectedLength) tuples covering all twelve months
     * in a normal year plus December in a leap year.
     */
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // Normal year (2000): short months = 30 days, long months (2, 5, 8, 11) = 31 days
            { 2000,  1, 28, 30 },   // January  — short month
            { 2000,  2, 28, 31 },   // February — long month (middle of Q1)
            { 2000,  3, 28, 30 },   // March    — short month
            { 2000,  4, 28, 30 },   // April    — short month
            { 2000,  5, 28, 31 },   // May      — long month (middle of Q2)
            { 2000,  6, 28, 30 },   // June     — short month
            { 2000,  7, 28, 30 },   // July     — short month
            { 2000,  8, 28, 31 },   // August   — long month (middle of Q3)
            { 2000,  9, 28, 30 },   // September — short month
            { 2000, 10, 28, 30 },   // October  — short month
            { 2000, 11, 28, 31 },   // November — long month (middle of Q4)
            { 2000, 12, 28, 30 },   // December — short month in a normal year
            // Leap year (2004): December gains a leap week, extending it to 37 days
            { 2004, 12, 20, 37 },   // December — extended month in a leap year
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int day, int length) {
        assertEquals(length, Symmetry010Date.of(year, month, day).lengthOfMonth());
    }
}
