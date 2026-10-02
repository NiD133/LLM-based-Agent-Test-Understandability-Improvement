package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_badDates {

    public static Object[][] data_badDates() {
        return new Object[][] {
            // St. Tib's Day (month=0, day=0) is only valid in leap years; 1900 is not a leap year
            { 1900, 0, 0 },

            // Invalid month values: negative, zero with non-zero day, or exceeding the 5-month limit
            { 1900, -1, 1 },
            { 1900, 0,  1 },
            { 1900, 6,  1 },
            { 1900, 7,  1 },

            // Invalid day-of-month values for a regular month (valid range: 1–73)
            { 1900, 1, -1 },
            { 1900, 1,  0 },
            { 1900, 1, 74 },

            // Second occurrence of St. Tib's Day in a non-leap year (duplicate case, still invalid)
            { 1900, 0,  0 },

            // Invalid day-of-month values for month 5 (valid range: 1–73)
            { 1900, 5, -1 },
            { 1900, 5,  0 },
            { 1900, 5, 74 },

            // Day exceeds 73 for the remaining regular months (2, 3, 4)
            { 1900, 2, 74 },
            { 1900, 3, 74 },
            { 1900, 4, 74 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(year, month, dom));
    }
}
