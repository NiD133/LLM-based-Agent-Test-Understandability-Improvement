package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry454Date#lengthOfMonth()} for the Symmetry454 calendar.
 *
 * In the Symmetry454 calendar each quarter follows a 4-5-4 week pattern:
 *   - Short months (4 weeks = 28 days): Jan, Mar, Apr, Jun, Jul, Sep, Oct, Dec*
 *   - Long  months (5 weeks = 35 days): Feb, May, Aug, Nov
 *   - December becomes a long month (35 days) in leap years (e.g. 2004).
 */
@SuppressWarnings({"static-method"})
public class TestSymmetry454Chronology_test_lengthOfMonth {

    // Each row: { year, month, dayWithinMonth, expectedLengthOfMonth }
    public static Object[][] data_lengthOfMonth() {
        return new Object[][] {
            // --- Quarter 1: Jan(28) Feb(35) Mar(28) ---
            { 2000,  1, 28, 28 }, // January  – short month (4 weeks)
            { 2000,  2, 28, 35 }, // February – long  month (5 weeks)
            { 2000,  3, 28, 28 }, // March    – short month (4 weeks)

            // --- Quarter 2: Apr(28) May(35) Jun(28) ---
            { 2000,  4, 28, 28 }, // April – short month
            { 2000,  5, 28, 35 }, // May   – long  month
            { 2000,  6, 28, 28 }, // June  – short month

            // --- Quarter 3: Jul(28) Aug(35) Sep(28) ---
            { 2000,  7, 28, 28 }, // July   – short month
            { 2000,  8, 28, 35 }, // August – long  month
            { 2000,  9, 28, 28 }, // September – short month

            // --- Quarter 4: Oct(28) Nov(35) Dec(28 / 35 in leap year) ---
            { 2000, 10, 28, 28 }, // October  – short month
            { 2000, 11, 28, 35 }, // November – long  month
            { 2000, 12, 28, 28 }, // December – short month in a non-leap year

            // December in a leap year gains an extra week (35 days)
            { 2004, 12, 20, 35 }, // December 2004 – long month because 2004 is a leap year
        };
    }

    @ParameterizedTest
    @MethodSource("data_lengthOfMonth")
    public void test_lengthOfMonth(int year, int month, int day, int length) {
        assertEquals(length, Symmetry454Date.of(year, month, day).lengthOfMonth());
    }
}
