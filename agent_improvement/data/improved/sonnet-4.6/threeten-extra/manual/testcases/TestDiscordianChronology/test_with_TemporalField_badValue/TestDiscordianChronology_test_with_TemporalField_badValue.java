package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.temporal.TemporalField;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_with_TemporalField_badValue {

    /**
     * Invalid (field, value) combinations that should cause DateTimeException.
     * Each row: year, month, dom, field, badValue
     *
     * DAY_OF_WEEK: valid range is 1–5 in a regular month; 0 is reserved for St. Tib's Day.
     * DAY_OF_MONTH: valid range is 1–73 in a regular month; 0 is reserved for St. Tib's Day.
     * DAY_OF_YEAR: valid range is 1–366 (leap) / 1–365 (non-leap); 0 is never valid.
     * MONTH_OF_YEAR: valid range is 0 (St. Tib's) or 1–5; negative values and 6+ are invalid.
     */
    public static Object[][] data_with_bad() {
        return new Object[][] {
            // DAY_OF_WEEK — non-leap year (2013): valid values are 1–5; 0 and 6 are out of range
            { 2013, 1, 1, DAY_OF_WEEK,  0 },
            { 2013, 1, 1, DAY_OF_WEEK,  6 },
            // DAY_OF_WEEK — leap year (2014): valid values are 1–5; -1 and 6 are out of range
            { 2014, 1, 1, DAY_OF_WEEK, -1 },
            { 2014, 1, 1, DAY_OF_WEEK,  6 },

            // DAY_OF_MONTH — non-leap year (2013): valid values are 1–73; 0 and 74 are out of range
            { 2013, 1, 1, DAY_OF_MONTH,  0 },
            { 2013, 1, 1, DAY_OF_MONTH, 74 },
            // DAY_OF_MONTH — leap year (2014): valid values are 1–73; -1 and 74 are out of range
            { 2014, 1, 1, DAY_OF_MONTH, -1 },
            { 2014, 1, 1, DAY_OF_MONTH, 74 },

            // DAY_OF_YEAR — non-leap year (2013): valid values are 1–365; 0 and 367 are out of range
            { 2013, 1, 1, DAY_OF_YEAR,   0 },
            { 2013, 1, 1, DAY_OF_YEAR, 367 },
            // DAY_OF_YEAR — leap year (2014): valid values are 1–366; 0 and 367 are out of range
            { 2014, 1, 1, DAY_OF_YEAR,   0 },
            { 2014, 1, 1, DAY_OF_YEAR, 367 },

            // MONTH_OF_YEAR — non-leap year (2013): valid values are 1–5; 0 and 6 are out of range
            { 2013, 1, 1, MONTH_OF_YEAR, 0 },
            { 2013, 1, 1, MONTH_OF_YEAR, 6 },
            // MONTH_OF_YEAR — leap year (2014): valid values are 0–5; -1 and 6 are out of range
            { 2014, 1, 1, MONTH_OF_YEAR, -1 },
            { 2014, 1, 1, MONTH_OF_YEAR,  6 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dom, TemporalField field, long value) {
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(year, month, dom).with(field, value));
    }
}
