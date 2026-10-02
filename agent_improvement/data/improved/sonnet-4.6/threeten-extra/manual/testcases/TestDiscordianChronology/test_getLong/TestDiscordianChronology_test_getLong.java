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

/**
 * Tests for {@link DiscordianDate#getLong(TemporalField)}.
 *
 * <p>The Discordian calendar has 5 months of 73 days each (365 days/year),
 * with a leap day called St. Tib's Day (month=0, day=0) inserted between
 * day 59 and 60 of month 1 in leap years.
 */
public class TestDiscordianChronology_test_getLong {

    /**
     * Test data: each row is (year, month, dayOfMonth, field, expectedValue).
     *
     * <p>Three representative dates are covered:
     * <ul>
     *   <li>2014-1-26 : a normal date in month 1 (Chaos), early in the year
     *   <li>2014-5-26 : a normal date in month 5 (Discord), late in the year
     *   <li>2014-0-0  : St. Tib's Day in leap year 2014 (intercalary, not in any month/week)
     * </ul>
     */
    public static Object[][] data_getLong() {
        // Discordian year 2014 is a leap year; month 1 = Chaos, month 5 = Discord.
        // St. Tib's Day is represented as month=0, day=0 and has ordinal day-of-year = 60.
        return new Object[][] {

            // --- Normal date: Chaos 26, YOLD 2014 (first month, day 26) ---
            // 2014 * 5 seasons + season 1 - 1 = proleptic month index
            { 2014, 1, 26, DAY_OF_WEEK,                  1L },
            { 2014, 1, 26, DAY_OF_MONTH,                 26L },
            { 2014, 1, 26, DAY_OF_YEAR,                  26L },
            { 2014, 1, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1L },
            { 2014, 1, 26, ALIGNED_WEEK_OF_MONTH,        6L },
            { 2014, 1, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,  1L },
            { 2014, 1, 26, ALIGNED_WEEK_OF_YEAR,         6L },
            { 2014, 1, 26, MONTH_OF_YEAR,                1L },
            { 2014, 1, 26, PROLEPTIC_MONTH,              2014L * 5 + 1 - 1 },
            { 2014, 1, 26, YEAR,                         2014L },
            { 2014, 1, 26, ERA,                          1L },

            // --- Normal date: Discord 26, YOLD 2014 (fifth month, day 26) ---
            // Day-of-year = 1 + 73*4 + 26 = 319 (four full seasons of 73 days, then day 26)
            { 2014, 5, 26, DAY_OF_WEEK,                  3L },
            { 2014, 5, 26, DAY_OF_MONTH,                 26L },
            { 2014, 5, 26, DAY_OF_YEAR,                  1L + 73 + 73 + 73 + 73 + 26 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1L },
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,        6L },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,  3L },
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,         64L },
            { 2014, 5, 26, MONTH_OF_YEAR,                5L },
            { 2014, 5, 26, PROLEPTIC_MONTH,              2014L * 5 + 5 - 1 },
            { 2014, 5, 26, YEAR,                         2014L },
            { 2014, 5, 26, ERA,                          1L },

            // ERA is always 1 regardless of year (only one era exists: YOLD)
            { 1,    5,  8, ERA,                          1L },

            // --- St. Tib's Day: intercalary leap day in YOLD 2014 (month=0, day=0) ---
            // This day is not part of any month or week, so all week/month fields return 0.
            // It falls as the 60th ordinal day of the year (between Chaos 59 and Chaos 60).
            { 2014, 0, 0, DAY_OF_WEEK,                   0L },
            { 2014, 0, 0, DAY_OF_MONTH,                  0L },
            { 2014, 0, 0, DAY_OF_YEAR,                   60L },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH,  0L },
            { 2014, 0, 0, ALIGNED_WEEK_OF_MONTH,         0L },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,   0L },
            { 2014, 0, 0, ALIGNED_WEEK_OF_YEAR,          0L },
            { 2014, 0, 0, MONTH_OF_YEAR,                 0L },
            // St. Tib's Day is within the first season's span, so proleptic month = season 1 index
            { 2014, 0, 0, PROLEPTIC_MONTH,               2014L * 5 + 1 - 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_getLong")
    public void test_getLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, DiscordianDate.of(year, month, dom).getLong(field));
    }
}
