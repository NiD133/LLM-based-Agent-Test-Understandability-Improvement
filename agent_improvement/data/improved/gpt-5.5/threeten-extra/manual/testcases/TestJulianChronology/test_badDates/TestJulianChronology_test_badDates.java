package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestJulianChronology_test_badDates {

    private static final int COMMON_YEAR = 1899;
    private static final int LEAP_YEAR = 1900;

    public static Object[][] data_badDates() {
        return new Object[][] {
                // Invalid month values.
                { LEAP_YEAR, 0, 0 },
                { LEAP_YEAR, -1, 1 },
                { LEAP_YEAR, 0, 1 },
                { LEAP_YEAR, 13, 1 },
                { LEAP_YEAR, 14, 1 },

                // Invalid days for January.
                { LEAP_YEAR, 1, -1 },
                { LEAP_YEAR, 1, 0 },
                { LEAP_YEAR, 1, 32 },

                // Invalid days for February in a leap year.
                { LEAP_YEAR, 2, -1 },
                { LEAP_YEAR, 2, 0 },
                { LEAP_YEAR, 2, 30 },
                { LEAP_YEAR, 2, 31 },
                { LEAP_YEAR, 2, 32 },

                // Invalid days for February in a common year.
                { COMMON_YEAR, 2, -1 },
                { COMMON_YEAR, 2, 0 },
                { COMMON_YEAR, 2, 29 },
                { COMMON_YEAR, 2, 30 },
                { COMMON_YEAR, 2, 31 },
                { COMMON_YEAR, 2, 32 },

                // Invalid days for December.
                { LEAP_YEAR, 12, -1 },
                { LEAP_YEAR, 12, 0 },
                { LEAP_YEAR, 12, 32 },

                // Invalid days for the remaining months.
                { LEAP_YEAR, 3, 32 },
                { LEAP_YEAR, 4, 31 },
                { LEAP_YEAR, 5, 32 },
                { LEAP_YEAR, 6, 31 },
                { LEAP_YEAR, 7, 32 },
                { LEAP_YEAR, 8, 32 },
                { LEAP_YEAR, 9, 31 },
                { LEAP_YEAR, 10, 32 },
                { LEAP_YEAR, 11, 31 },
        };
    }

    @ParameterizedTest(name = "JulianDate.of({0}, {1}, {2}) rejects an invalid date")
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dayOfMonth) {
        assertThrows(DateTimeException.class, () -> JulianDate.of(year, month, dayOfMonth));
    }
}
