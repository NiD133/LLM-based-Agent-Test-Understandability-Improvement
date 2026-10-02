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
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link DiscordianDate#with(TemporalField, long)}: setting a single
 * field on a Discordian date should yield the expected Discordian date.
 *
 * <p>The Discordian calendar has 5 seasons ("months") of 73 days each. In a leap
 * year an extra day, St. Tib's Day, sits outside the normal season/day structure
 * and is encoded as month 0, day 0. Many of the cases below exercise how
 * {@code with(...)} behaves around St. Tib's Day.
 */
public class TestDiscordianChronology_test_with_TemporalField {

    /**
     * Cases for {@link #test_with_TemporalField}.
     *
     * <p>Each row is: the starting Discordian date {@code (year, month, dom)},
     * the {@link TemporalField} to set and the {@code value} to set it to, then
     * the expected resulting Discordian date {@code (expectedYear, expectedMonth, expectedDom)}.
     */
    public static Object[][] data_with() {
        return new Object[][] {
            // start            field                         value              expected result
            { 2014, 5, 26, DAY_OF_WEEK,                 1L, 2014, 5, 24 },
            { 2014, 5, 26, DAY_OF_WEEK,                 3L, 2014, 5, 26 },
            { 2014, 5, 26, DAY_OF_MONTH,                31L, 2014, 5, 31 },
            { 2014, 5, 26, DAY_OF_MONTH,                26L, 2014, 5, 26 },
            { 2014, 5, 26, DAY_OF_YEAR,                 365L, 2014, 5, 72 },
            { 2014, 5, 26, DAY_OF_YEAR,                 319L, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3L, 2014, 5, 28 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1L, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,       1L, 2014, 5, 1 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,       6L, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2L, 2014, 5, 25 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3L, 2014, 5, 26 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,        23L, 2014, 2, 40 },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,        64L, 2014, 5, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR,               4L, 2014, 4, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR,               5L, 2014, 5, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH,             2013L * 5 + 3 - 1, 2013, 3, 26 },
            { 2014, 5, 26, PROLEPTIC_MONTH,             2014L * 5 + 5 - 1, 2014, 5, 26 },
            { 2014, 5, 26, YEAR,                        2012L, 2012, 5, 26 },
            { 2014, 5, 26, YEAR,                        2014L, 2014, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA,                 2012L, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA,                 2014L, 2014, 5, 26 },
            { 2014, 5, 26, ERA,                         1L, 2014, 5, 26 },

            // Starting on St. Tib's Day (month 0, day 0) in leap year 2014.
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0L, 2014, 0, 0 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1L, 2014, 1, 56 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2L, 2014, 1, 57 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3L, 2014, 1, 58 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4L, 2014, 1, 59 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5L, 2014, 1, 60 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  0L, 2014, 0, 0 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  1L, 2014, 1, 56 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  2L, 2014, 1, 57 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  3L, 2014, 1, 58 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  4L, 2014, 1, 59 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  5L, 2014, 1, 60 },
            { 2014, 0, 0, ALIGNED_WEEK_OF_MONTH,        0L, 2014, 0, 0 },
            { 2014, 0, 0, ALIGNED_WEEK_OF_MONTH,        3L, 2014, 1, 15 },
            { 2014, 0, 0, ALIGNED_WEEK_OF_YEAR,         0L, 2014, 0, 0 },
            { 2014, 0, 0, ALIGNED_WEEK_OF_YEAR,         3L, 2014, 1, 15 },
            { 2014, 0, 0, DAY_OF_WEEK,                  0L, 2014, 0, 0 },
            { 2014, 0, 0, DAY_OF_WEEK,                  1L, 2014, 1, 56 },
            { 2014, 0, 0, DAY_OF_WEEK,                  2L, 2014, 1, 57 },
            { 2014, 0, 0, DAY_OF_WEEK,                  3L, 2014, 1, 58 },
            { 2014, 0, 0, DAY_OF_WEEK,                  4L, 2014, 1, 59 },
            { 2014, 0, 0, DAY_OF_WEEK,                  5L, 2014, 1, 60 },
            { 2014, 0, 0, DAY_OF_MONTH,                 0L, 2014, 0, 0 },
            { 2014, 0, 0, DAY_OF_MONTH,                 3L, 2014, 1, 3 },
            { 2014, 0, 0, MONTH_OF_YEAR,                0L, 2014, 0, 0 },
            { 2014, 0, 0, MONTH_OF_YEAR,                1L, 2014, 1, 60 },
            { 2014, 0, 0, MONTH_OF_YEAR,                2L, 2014, 2, 60 },
            { 2014, 0, 0, YEAR,                         2014L, 2014, 0, 0 },
            { 2014, 0, 0, YEAR,                         2013L, 2013, 1, 60 },
            { 2014, 0, 0, YEAR,                         2015L, 2015, 1, 60 },
            { 2014, 0, 0, YEAR,                         2018L, 2018, 0, 0 },

            // Setting DAY_OF_MONTH/MONTH_OF_YEAR/DAY_OF_YEAR onto / off of St. Tib's Day.
            { 2014, 3, 31, DAY_OF_MONTH,                0L, 2014, 0, 0 },
            { 2014, 1, 31, DAY_OF_MONTH,                0L, 2014, 0, 0 },
            { 2014, 3, 31, MONTH_OF_YEAR,               0L, 2014, 0, 0 },
            { 2014, 3, 31, DAY_OF_YEAR,                 60L, 2014, 0, 0 },
            { 2013, 3, 31, DAY_OF_YEAR,                 60L, 2013, 1, 60 },
            { 2013, 1, 60, YEAR,                        2014L, 2014, 1, 60 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(int year, int month, int dom, TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        DiscordianDate start = DiscordianDate.of(year, month, dom);
        DiscordianDate expected = DiscordianDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.with(field, value));
    }
}
