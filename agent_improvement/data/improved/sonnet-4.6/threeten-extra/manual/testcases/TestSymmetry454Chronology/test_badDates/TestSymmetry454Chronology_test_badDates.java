package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry454Date#of(int, int, int)} rejects invalid year/month/day
 * combinations by throwing {@link DateTimeException}.
 *
 * <p>In the Symmetry454 calendar:
 * <ul>
 *   <li>Valid months are 1–12.</li>
 *   <li>Short months (1, 3, 4, 6, 7, 9, 10) have 28 days; long months (2, 5, 8, 11) have 35 days.</li>
 *   <li>Month 12 has 28 days in a normal year and 35 days in a leap year.</li>
 * </ul>
 */
@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_badDates {

    /**
     * Provides invalid (year, month, dayOfMonth) triples that must be rejected.
     * Cases are grouped by the type of invalid field.
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // --- Invalid month (out of 1–12 range) ---
            { 2000,  0, 1 },   // month 0 is below the valid range
            { 2000, -1, 0 },   // negative month and zero day
            { 2000, -1, 1 },   // negative month
            { 2000, -2, 1 },   // more negative month
            { 2000, 13, 1 },   // month 13 exceeds the 12-month year
            { 2000, 15, 1 },   // month 15 exceeds the 12-month year
            {   -1, 13, 28 },  // negative year, month out of range
            {   -1, 13, 29 },  // negative year, month out of range, day out of range

            // --- Invalid day (zero or negative) ---
            { 2000, 1,  0 },   // day 0 is below the minimum
            { 2000, 1, -1 },   // negative day

            // --- Day exceeds the length of each short month (28 days) ---
            // Short months: 1, 3, 4, 6, 7, 9, 10 (and month 12 in non-leap years)
            { 2000,  1, 29 },  // month 1  has 28 days; day 29 is invalid
            { 2000,  3, 29 },  // month 3  has 28 days; day 29 is invalid
            { 2000,  4, 29 },  // month 4  has 28 days; day 29 is invalid
            { 2000,  6, 29 },  // month 6  has 28 days; day 29 is invalid
            { 2000,  7, 29 },  // month 7  has 28 days; day 29 is invalid
            { 2000,  9, 29 },  // month 9  has 28 days; day 29 is invalid
            { 2000, 10, 29 },  // month 10 has 28 days; day 29 is invalid
            { 2000, 12, 29 },  // month 12 has 28 days in 2000 (non-leap year); day 29 is invalid

            // --- Day exceeds the length of each long month (35 days) ---
            // Long months: 2, 5, 8, 11 (and month 12 in leap years)
            { 2000,  2, 36 },  // month 2  has 35 days; day 36 is invalid
            { 2000,  5, 36 },  // month 5  has 35 days; day 36 is invalid
            { 2000,  8, 36 },  // month 8  has 35 days; day 36 is invalid
            { 2000, 11, 36 },  // month 11 has 35 days; day 36 is invalid
            { 2004, 12, 36 },  // month 12 has 35 days in 2004 (leap year); day 36 is invalid
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> Symmetry454Date.of(year, month, dom));
    }
}
