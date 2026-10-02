package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Symmetry454Date#of(int, int, int)} rejects out-of-range
 * year/month/day combinations by throwing a {@link DateTimeException}.
 * <p>
 * In the Symmetry454 calendar each month has 28 days, except the middle month of
 * each quarter (February, May, August, November) which has 35 days, and December
 * which also has 35 days in a leap year.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_badDates {

    /**
     * Invalid {year, month, dayOfMonth} triples that must be rejected.
     * Each row is grouped by the reason it is invalid.
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // month outside the valid range 1..12
            { -1, 13, 28 },
            { -1, 13, 29 },
            { 2000, -2, 1 },
            { 2000, 13, 1 },
            { 2000, 15, 1 },
            { 2000, 0, 1 },
            { 2000, -1, 0 },
            { 2000, -1, 1 },

            // day-of-month below the valid minimum of 1
            { 2000, 1, -1 },
            { 2000, 1, 0 },

            // day-of-month above the length of the month
            { 2000, 1, 29 },  // January  has 28 days
            { 2000, 2, 36 },  // February has 35 days
            { 2000, 3, 29 },  // March    has 28 days
            { 2000, 4, 29 },  // April    has 28 days
            { 2000, 5, 36 },  // May      has 35 days
            { 2000, 6, 29 },  // June     has 28 days
            { 2000, 7, 29 },  // July     has 28 days
            { 2000, 8, 36 },  // August   has 35 days
            { 2000, 9, 29 },  // September has 28 days
            { 2000, 10, 29 }, // October  has 28 days
            { 2000, 11, 36 }, // November has 35 days
            { 2000, 12, 29 }, // December has 28 days in the non-leap year 2000
            { 2004, 12, 36 }, // December has 35 days in the leap year 2004
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dayOfMonth) {
        assertThrows(DateTimeException.class, () -> Symmetry454Date.of(year, month, dayOfMonth));
    }
}
