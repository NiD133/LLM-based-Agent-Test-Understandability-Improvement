package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link InternationalFixedDate#of(int, int, int)} rejects
 * year/month/day combinations that do not describe a valid date.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_badDates {

    /**
     * Provides {year, month, dayOfMonth} triples that must be rejected as invalid.
     * The International Fixed calendar has 13 months; months 1-12 have 28 days,
     * month 13 has 28 days plus a 29th "Year Day", and month 6 gains a 29th
     * "Leap Day" only in leap years (1900 is not a leap year).
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // year out of range (must be a positive proleptic year)
            { -1, 13, 28 },
            { -1, 13, 29 },
            { 0, 1, 1 },

            // month out of range (valid months are 1..13)
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

            // day out of range for month 1 (valid days are 1..28)
            { 1900, 1, -1 },
            { 1900, 1, 0 },
            { 1900, 1, 29 },

            // 29th day is invalid in every ordinary 28-day month of a non-leap year
            { 1900, 2, 29 },
            { 1900, 3, 29 },
            { 1900, 4, 29 },
            { 1900, 5, 29 },
            { 1900, 6, 29 },
            { 1900, 7, 29 },
            { 1900, 8, 29 },
            { 1900, 9, 29 },
            { 1900, 10, 29 },
            { 1900, 11, 29 },
            { 1900, 12, 29 },

            // month 13 has at most 29 days (the Year Day), so 30 is invalid
            { 1900, 13, 30 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> InternationalFixedDate.of(year, month, dom));
    }
}
