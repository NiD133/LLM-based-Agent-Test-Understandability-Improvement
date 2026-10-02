package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_badDates {

    public static Object[][] data_badDates() {
        return new Object[][] {
            // invalid month values
            { 1900,  0,  0 },
            { 1900, -1,  1 },
            { 1900,  0,  1 },
            { 1900, 13,  1 },
            { 1900, 14,  1 },

            // invalid day-of-month for January (31-day month)
            { 1900,  1, -1 },
            { 1900,  1,  0 },
            { 1900,  1, 32 },

            // invalid day-of-month for February in a Julian leap year (1900 is a leap year in Julian)
            { 1900,  2, -1 },
            { 1900,  2,  0 },
            { 1900,  2, 30 },
            { 1900,  2, 31 },
            { 1900,  2, 32 },

            // invalid day-of-month for February in a non-leap year (1899)
            { 1899,  2, -1 },
            { 1899,  2,  0 },
            { 1899,  2, 29 },
            { 1899,  2, 30 },
            { 1899,  2, 31 },
            { 1899,  2, 32 },

            // invalid day-of-month for December (31-day month)
            { 1900, 12, -1 },
            { 1900, 12,  0 },
            { 1900, 12, 32 },

            // invalid day-of-month for other 31-day months (March, May, July, August, October)
            { 1900,  3, 32 },
            { 1900,  5, 32 },
            { 1900,  7, 32 },
            { 1900,  8, 32 },
            { 1900, 10, 32 },

            // invalid day-of-month for 30-day months (April, June, September, November)
            { 1900,  4, 31 },
            { 1900,  6, 31 },
            { 1900,  9, 31 },
            { 1900, 11, 31 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> JulianDate.of(year, month, dom));
    }
}
