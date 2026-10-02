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

public class TestDiscordianChronology_test_with_TemporalField {

    /**
     * Test data for {@link #test_with_TemporalField}.
     *
     * Each row: (year, month, dom, field, value, expectedYear, expectedMonth, expectedDom)
     *
     * The Discordian calendar has 5 months of 73 days each (365 days/year), plus
     * St. Tib's Day (month=0, day=0) inserted in leap years between day 59 and 60
     * of the first month.  All cases below use year 2014 (a leap year).
     */
    public static Object[][] data_with() {
        return new Object[][] {

            // ---- Regular date: 2014-5-26 (Aftermath, day 26) ----
            // Changing day-of-week moves to the nearest matching weekday within the same 5-day week
            { 2014, 5, 26, DAY_OF_WEEK,                  1, 2014, 5, 24 },  // move back to weekday 1
            { 2014, 5, 26, DAY_OF_WEEK,                  3, 2014, 5, 26 },  // already weekday 3, no change

            // Changing day-of-month within the same month
            { 2014, 5, 26, DAY_OF_MONTH,                31, 2014, 5, 31 },
            { 2014, 5, 26, DAY_OF_MONTH,                26, 2014, 5, 26 },  // no change

            // Changing day-of-year recomputes month+day from ordinal
            { 2014, 5, 26, DAY_OF_YEAR,                365, 2014, 5, 72 },  // last day of month 5
            { 2014, 5, 26, DAY_OF_YEAR,                319, 2014, 5, 26 },  // no change

            // Changing aligned-day-of-week-in-month shifts within the same 5-day block of the month
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 28 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2014, 5, 26 },  // no change

            // Changing aligned-week-of-month selects a different 5-day block within the month
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,        1, 2014, 5,  1 },  // first week of month
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH,        6, 2014, 5, 26 },  // no change

            // Changing aligned-day-of-week-in-year shifts within the same 5-day block of the year
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,  2, 2014, 5, 25 },
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR,  3, 2014, 5, 26 },  // no change

            // Changing aligned-week-of-year selects a different 5-day block within the year
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,         23, 2014, 2, 40 },  // lands in month 2
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR,         64, 2014, 5, 26 },  // no change

            // Changing month-of-year, keeping day-of-month
            { 2014, 5, 26, MONTH_OF_YEAR,                 4, 2014, 4, 26 },
            { 2014, 5, 26, MONTH_OF_YEAR,                 5, 2014, 5, 26 },  // no change

            // Changing proleptic-month (absolute month index from epoch)
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 5 + 3 - 1, 2013, 3, 26 },  // different year+month
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 5 + 5 - 1, 2014, 5, 26 },  // no change

            // Changing year, keeping month and day
            { 2014, 5, 26, YEAR,      2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR,      2014, 2014, 5, 26 },  // no change

            // Changing year-of-era (same as YEAR for the single Discordian era)
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 },
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 },  // no change

            // ERA is always 1 (YOLD) so setting it is a no-op
            { 2014, 5, 26, ERA,           1, 2014, 5, 26 },

            // ---- St. Tib's Day: 2014-0-0 (the intercalary leap day) ----
            // Aligned-day-of-week-in-month: 0 stays on St. Tib's; non-zero moves into month 1
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0, 2014, 0,  0 },  // no change
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2014, 1, 56 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 2014, 1, 57 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 1, 58 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4, 2014, 1, 59 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 1, 60 },

            // Aligned-day-of-week-in-year: mirrors aligned-day-of-week-in-month for St. Tib's
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  0, 2014, 0,  0 },  // no change
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  1, 2014, 1, 56 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  2, 2014, 1, 57 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  3, 2014, 1, 58 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  4, 2014, 1, 59 },
            { 2014, 0, 0, ALIGNED_DAY_OF_WEEK_IN_YEAR,  5, 2014, 1, 60 },

            // Aligned-week-of-month: 0 stays on St. Tib's; non-zero lands in month 1
            { 2014, 0, 0, ALIGNED_WEEK_OF_MONTH,         0, 2014, 0,  0 },  // no change
            { 2014, 0, 0, ALIGNED_WEEK_OF_MONTH,         3, 2014, 1, 15 },

            // Aligned-week-of-year: 0 stays on St. Tib's; non-zero lands in month 1
            { 2014, 0, 0, ALIGNED_WEEK_OF_YEAR,          0, 2014, 0,  0 },  // no change
            { 2014, 0, 0, ALIGNED_WEEK_OF_YEAR,          3, 2014, 1, 15 },

            // Day-of-week: 0 stays on St. Tib's; non-zero moves into the surrounding week in month 1
            { 2014, 0, 0, DAY_OF_WEEK,                   0, 2014, 0,  0 },  // no change
            { 2014, 0, 0, DAY_OF_WEEK,                   1, 2014, 1, 56 },
            { 2014, 0, 0, DAY_OF_WEEK,                   2, 2014, 1, 57 },
            { 2014, 0, 0, DAY_OF_WEEK,                   3, 2014, 1, 58 },
            { 2014, 0, 0, DAY_OF_WEEK,                   4, 2014, 1, 59 },
            { 2014, 0, 0, DAY_OF_WEEK,                   5, 2014, 1, 60 },

            // Day-of-month: 0 stays on St. Tib's; non-zero moves into month 1
            { 2014, 0, 0, DAY_OF_MONTH,                  0, 2014, 0,  0 },  // no change
            { 2014, 0, 0, DAY_OF_MONTH,                  3, 2014, 1,  3 },

            // Month-of-year: 0 stays on St. Tib's; non-zero sets month, keeping the "60th position"
            { 2014, 0, 0, MONTH_OF_YEAR,                 0, 2014, 0,  0 },  // no change
            { 2014, 0, 0, MONTH_OF_YEAR,                 1, 2014, 1, 60 },
            { 2014, 0, 0, MONTH_OF_YEAR,                 2, 2014, 2, 60 },

            // Changing year from a leap year's St. Tib's Day:
            //  - same leap year → stays on St. Tib's Day
            //  - non-leap year → moves to the nearest non-leap equivalent (1-60)
            { 2014, 0, 0, YEAR,   2014, 2014, 0,  0 },  // same leap year, no change
            { 2014, 0, 0, YEAR,   2013, 2013, 1, 60 },  // non-leap: map to 1-60
            { 2014, 0, 0, YEAR,   2015, 2015, 1, 60 },  // non-leap: map to 1-60
            { 2014, 0, 0, YEAR,   2018, 2018, 0,  0 },  // another leap year: stays St. Tib's

            // ---- Changing DAY_OF_MONTH to 0 on a regular date yields St. Tib's Day ----
            { 2014, 3, 31, DAY_OF_MONTH,   0, 2014, 0,  0 },
            { 2014, 1, 31, DAY_OF_MONTH,   0, 2014, 0,  0 },

            // ---- Changing MONTH_OF_YEAR to 0 yields St. Tib's Day ----
            { 2014, 3, 31, MONTH_OF_YEAR,  0, 2014, 0,  0 },

            // ---- Changing DAY_OF_YEAR to 60 (the leap day ordinal) yields St. Tib's Day ----
            { 2014, 3, 31, DAY_OF_YEAR,   60, 2014, 0,  0 },  // leap year → St. Tib's
            { 2013, 3, 31, DAY_OF_YEAR,   60, 2013, 1, 60 },  // non-leap year → day 60 of month 1

            // ---- Changing year when source is a regular date crossing a St. Tib's boundary ----
            { 2013, 1, 60, YEAR, 2014, 2014, 1, 60 },  // non-leap → leap year, day 60 stays in month 1
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(
            int year, int month, int dom,
            TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
            DiscordianDate.of(expectedYear, expectedMonth, expectedDom),
            DiscordianDate.of(year, month, dom).with(field, value));
    }
}
