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

    public static Object[][] data_with_bad() {
        return new Object[][] {
                // Day-of-week must be in the Discordian range 1 to 5.
                {2013, 1, 1, DAY_OF_WEEK, 0},
                {2013, 1, 1, DAY_OF_WEEK, 6},
                {2014, 1, 1, DAY_OF_WEEK, -1},
                {2014, 1, 1, DAY_OF_WEEK, 6},

                // Day-of-month must be in the Discordian range 1 to 73.
                {2013, 1, 1, DAY_OF_MONTH, 0},
                {2013, 1, 1, DAY_OF_MONTH, 74},
                {2014, 1, 1, DAY_OF_MONTH, -1},
                {2014, 1, 1, DAY_OF_MONTH, 74},

                // Day-of-year must fit the current Discordian year length.
                {2013, 1, 1, DAY_OF_YEAR, 0},
                {2014, 1, 1, DAY_OF_YEAR, 0},
                {2013, 1, 1, DAY_OF_YEAR, 367},
                {2014, 1, 1, DAY_OF_YEAR, 367},

                // Month-of-year must be in the normal Discordian range 1 to 5.
                {2013, 1, 1, MONTH_OF_YEAR, 0},
                {2013, 1, 1, MONTH_OF_YEAR, 6},
                {2014, 1, 1, MONTH_OF_YEAR, -1},
                {2014, 1, 1, MONTH_OF_YEAR, 6},
        };
    }

    @ParameterizedTest
    @MethodSource("data_with_bad")
    public void test_with_TemporalField_badValue(int year, int month, int dayOfMonth, TemporalField field, long value) {
        assertThrows(DateTimeException.class, () -> DiscordianDate.of(year, month, dayOfMonth).with(field, value));
    }
}
