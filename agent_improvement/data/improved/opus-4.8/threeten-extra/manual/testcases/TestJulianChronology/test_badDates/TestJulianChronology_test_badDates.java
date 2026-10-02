package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link JulianDate#of(int, int, int)} rejects field values that do
 * not correspond to a real date in the Julian calendar.
 */
public class TestJulianChronology_test_badDates {

    /**
     * Supplies invalid {year, month, dayOfMonth} combinations that must be
     * rejected by {@link JulianDate#of(int, int, int)}.
     */
    public static Object[][] data_badDates() {
        return new Object[][] {
            // month out of the 1..12 range
            { 1900, 0, 0 },
            { 1900, -1, 1 },
            { 1900, 0, 1 },
            { 1900, 13, 1 },
            { 1900, 14, 1 },

            // day-of-month out of range for January (31 days)
            { 1900, 1, -1 },
            { 1900, 1, 0 },
            { 1900, 1, 32 },

            // day-of-month out of range for February in a leap year (29 days)
            { 1900, 2, -1 },
            { 1900, 2, 0 },
            { 1900, 2, 30 },
            { 1900, 2, 31 },
            { 1900, 2, 32 },

            // day-of-month out of range for February in a non-leap year (28 days)
            { 1899, 2, -1 },
            { 1899, 2, 0 },
            { 1899, 2, 29 },
            { 1899, 2, 30 },
            { 1899, 2, 31 },
            { 1899, 2, 32 },

            // day-of-month out of range for December (31 days)
            { 1900, 12, -1 },
            { 1900, 12, 0 },
            { 1900, 12, 32 },

            // a day past the end of every other month
            { 1900, 3, 32 },
            { 1900, 4, 31 },
            { 1900, 5, 32 },
            { 1900, 6, 31 },
            { 1900, 7, 32 },
            { 1900, 8, 32 },
            { 1900, 9, 31 },
            { 1900, 10, 32 },
            { 1900, 11, 31 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dayOfMonth) {
        assertThrows(DateTimeException.class, () -> JulianDate.of(year, month, dayOfMonth));
    }
}
