package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link InternationalFixedDate#of(int, int, int)} rejects
 * year/month/day combinations that do not correspond to a valid date in the
 * International Fixed calendar.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_badDates {

    /**
     * Each row is a {year, month, dayOfMonth} triple that is invalid, for one of
     * these reasons:
     * <ul>
     *   <li>year is zero or negative (the calendar has no proleptic year 0);</li>
     *   <li>month is outside the range 1..13;</li>
     *   <li>day is outside the range valid for that month (regular months have
     *       28 days; month 13 has 29 days; month 6 has 29 days only in a leap
     *       year, and 1900 is not a leap year).</li>
     * </ul>
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // invalid year (zero or negative)
            { -1, 13, 28 },
            { -1, 13, 29 },
            { 0, 1, 1 },

            // invalid month (outside 1..13)
            { 1900, -2, 1 },
            { 1900, 14, 1 },
            { 1900, 15, 1 },
            { 1904, -1, -2 },
            { 1904, -1, 0 },
            { 1904, -1, 1 },
            { 1900, -1, 0 },
            { 1900, -1, -2 },
            { 1900, 0, -1 },
            { 1900, 0, 1 },
            { 1900, 0, 2 },

            // invalid day of month
            { 1900, 1, -1 },
            { 1900, 1, 0 },
            { 1900, 1, 29 },
            { 1900, 2, 29 },
            { 1900, 3, 29 },
            { 1900, 4, 29 },
            { 1900, 5, 29 },
            { 1900, 6, 29 },   // 1900 is not a leap year, so month 6 has only 28 days
            { 1900, 7, 29 },
            { 1900, 8, 29 },
            { 1900, 9, 29 },
            { 1900, 10, 29 },
            { 1900, 11, 29 },
            { 1900, 12, 29 },
            { 1900, 13, 30 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, month, dom));
    }
}
