package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_getLong {

    private static final int LEAP_YEAR = 2014;
    private static final int FIRST_MONTH = 1;
    private static final int FIFTH_MONTH = 5;
    private static final int ST_TIBS_MONTH = 0;
    private static final int ST_TIBS_DAY = 0;
    private static final int SAMPLE_DAY = 26;

    //-----------------------------------------------------------------------
    public static Object[][] data_getLong() {
        return new Object[][] {
                // First month, 26th day.
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, DAY_OF_WEEK, 1 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, DAY_OF_MONTH, 26 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, DAY_OF_YEAR, 26 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, ALIGNED_WEEK_OF_MONTH, 6 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, ALIGNED_WEEK_OF_YEAR, 6 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, MONTH_OF_YEAR, 1 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, PROLEPTIC_MONTH, LEAP_YEAR * 5 + 1 - 1 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, YEAR, 2014 },
                { LEAP_YEAR, FIRST_MONTH, SAMPLE_DAY, ERA, 1 },

                // Fifth month, 26th day.
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, DAY_OF_WEEK, 3 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, DAY_OF_MONTH, 26 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, DAY_OF_YEAR, 1 + 73 + 73 + 73 + 73 + 26 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, ALIGNED_WEEK_OF_MONTH, 6 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, ALIGNED_WEEK_OF_YEAR, 64 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, MONTH_OF_YEAR, 5 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, PROLEPTIC_MONTH, LEAP_YEAR * 5 + 5 - 1 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, YEAR, 2014 },
                { LEAP_YEAR, FIFTH_MONTH, SAMPLE_DAY, ERA, 1 },

                // Lower-bound year still belongs to the only Discordian era.
                { 1, FIFTH_MONTH, 8, ERA, 1 },

                // St. Tib's Day in a leap year.
                { LEAP_YEAR, ST_TIBS_MONTH, ST_TIBS_DAY, DAY_OF_WEEK, 0 },
                { LEAP_YEAR, ST_TIBS_MONTH, ST_TIBS_DAY, DAY_OF_MONTH, 0 },
                { LEAP_YEAR, ST_TIBS_MONTH, ST_TIBS_DAY, DAY_OF_YEAR, 60 },
                { LEAP_YEAR, ST_TIBS_MONTH, ST_TIBS_DAY, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0 },
                { LEAP_YEAR, ST_TIBS_MONTH, ST_TIBS_DAY, ALIGNED_WEEK_OF_MONTH, 0 },
                { LEAP_YEAR, ST_TIBS_MONTH, ST_TIBS_DAY, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0 },
                { LEAP_YEAR, ST_TIBS_MONTH, ST_TIBS_DAY, ALIGNED_WEEK_OF_YEAR, 0 },
                { LEAP_YEAR, ST_TIBS_MONTH, ST_TIBS_DAY, MONTH_OF_YEAR, 0 },
                { LEAP_YEAR, ST_TIBS_MONTH, ST_TIBS_DAY, PROLEPTIC_MONTH, LEAP_YEAR * 5 + 1 - 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        DiscordianDate date = DiscordianDate.of(year, month, dom);

        assertEquals(expected, date.getLong(field));
    }
}
