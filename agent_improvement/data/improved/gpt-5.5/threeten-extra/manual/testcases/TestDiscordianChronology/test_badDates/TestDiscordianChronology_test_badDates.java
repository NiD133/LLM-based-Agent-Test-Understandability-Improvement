package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_badDates {

    public static Object[][] data_badDates() {
        return new Object[][] {
                // St. Tib's Day is the only valid date with month/day zero.
                { 1900, 0, 0 },

                // Month must be St. Tib's Day (0) or one of the five seasons.
                { 1900, -1, 1 },
                { 1900, 0, 1 },
                { 1900, 6, 1 },
                { 1900, 7, 1 },

                // Day-of-month must be 1 through 73 for regular seasons.
                { 1900, 1, -1 },
                { 1900, 1, 0 },
                { 1900, 1, 74 },
                { 1900, 0, 0 },
                { 1900, 5, -1 },
                { 1900, 5, 0 },
                { 1900, 5, 74 },
                { 1900, 2, 74 },
                { 1900, 3, 74 },
                { 1900, 4, 74 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_badDates")
    public void test_badDates(int year, int month, int dom) {
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(year, month, dom));
    }
}
