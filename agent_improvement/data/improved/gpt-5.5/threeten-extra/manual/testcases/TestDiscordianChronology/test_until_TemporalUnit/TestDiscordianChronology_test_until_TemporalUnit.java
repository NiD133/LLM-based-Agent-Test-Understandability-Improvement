package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_until_TemporalUnit {

    public static Object[][] data_until() {
        return new Object[][] {
                // Same date and same-month day differences.
                {2014, 5, 26, 2014, 5, 26, DAYS, 0},
                {2014, 5, 26, 2014, 5, 32, DAYS, 6},
                {2014, 5, 26, 2014, 5, 20, DAYS, -6},
                {2014, 5, 26, 2014, 5, 26, WEEKS, 0},
                {2014, 5, 26, 2014, 5, 30, WEEKS, 0},
                {2014, 5, 26, 2014, 5, 31, WEEKS, 1},
                {2014, 5, 26, 2014, 5, 26, MONTHS, 0},
                {2014, 5, 26, 2015, 1, 25, MONTHS, 0},
                {2014, 5, 26, 2015, 1, 26, MONTHS, 1},

                // Whole-year based units only count complete units.
                {2014, 5, 26, 2014, 5, 26, YEARS, 0},
                {2014, 5, 26, 2015, 5, 25, YEARS, 0},
                {2014, 5, 26, 2015, 5, 26, YEARS, 1},
                {2014, 5, 26, 2014, 5, 26, DECADES, 0},
                {2014, 5, 26, 2024, 5, 25, DECADES, 0},
                {2014, 5, 26, 2024, 5, 26, DECADES, 1},
                {2014, 5, 26, 2014, 5, 26, CENTURIES, 0},
                {2014, 5, 26, 2114, 5, 25, CENTURIES, 0},
                {2014, 5, 26, 2114, 5, 26, CENTURIES, 1},
                {2014, 5, 26, 2014, 5, 26, MILLENNIA, 0},
                {2014, 5, 26, 3014, 5, 25, MILLENNIA, 0},
                {2014, 5, 26, 3014, 5, 26, MILLENNIA, 1},
                {2013, 5, 26, 3014, 5, 26, ERAS, 0},

                // St. Tib's Day sits between day 59 and day 60 in leap years.
                {2014, 1, 59, 2014, 1, 60, DAYS, 2},
                {2014, 1, 59, 2014, 0, 0, DAYS, 1},
                {2014, 0, 0, 2014, 1, 60, DAYS, 1},
                {2014, 1, 60, 2014, 1, 55, DAYS, -6},
                {2014, 0, 0, 2014, 0, 0, WEEKS, 0},
                {2014, 1, 60, 2014, 1, 60, WEEKS, 0},
                {2014, 1, 60, 2014, 1, 59, WEEKS, 0},
                {2014, 1, 60, 2014, 1, 56, WEEKS, 0},
                {2014, 1, 60, 2014, 1, 55, WEEKS, -1},
                {2014, 0, 0, 2014, 1, 54, WEEKS, -1},
                {2014, 0, 0, 2014, 1, 55, WEEKS, 0},
                {2014, 0, 0, 2014, 1, 64, WEEKS, 0},
                {2014, 0, 0, 2014, 1, 65, WEEKS, 1},
                {2014, 1, 54, 2014, 0, 0, WEEKS, 1},
                {2014, 1, 55, 2014, 0, 0, WEEKS, 0},
                {2014, 1, 64, 2014, 0, 0, WEEKS, 0},
                {2014, 1, 65, 2014, 0, 0, WEEKS, -1},
                {2014, 0, 0, 2014, 0, 0, MONTHS, 0},
                {2014, 0, 0, 2014, 2, 59, MONTHS, 0},
                {2014, 0, 0, 2014, 2, 60, MONTHS, 1},
                {2014, 2, 60, 2014, 0, 0, MONTHS, -1},
                {2014, 2, 59, 2014, 0, 0, MONTHS, 0},
                {2013, 5, 59, 2014, 0, 0, MONTHS, 1},
                {2013, 5, 60, 2014, 0, 0, MONTHS, 0},
                {2013, 5, 60, 2014, 1, 60, MONTHS, 1},
                {2014, 0, 0, 2014, 0, 0, YEARS, 0},
                {2014, 0, 0, 2015, 1, 59, YEARS, 0},
                {2014, 0, 0, 2015, 1, 60, YEARS, 1},
                {2013, 1, 60, 2014, 0, 0, YEARS, 0},
                {2013, 1, 59, 2014, 0, 0, YEARS, 1},
                {2013, 1, 60, 2014, 1, 60, YEARS, 1},
                {2014, 0, 0, 2013, 1, 59, YEARS, -1},
                {2014, 0, 0, 2013, 1, 60, YEARS, 0},
                {2015, 1, 60, 2014, 0, 0, YEARS, -1},
                {2015, 1, 59, 2014, 0, 0, YEARS, 0},
                {2018, 0, 0, 2014, 0, 0, YEARS, -4},
                {2014, 0, 0, 2018, 0, 0, YEARS, 4},
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int startYear,
            int startMonth,
            int startDayOfMonth,
            int endYear,
            int endMonth,
            int endDayOfMonth,
            TemporalUnit unit,
            long expected) {

        DiscordianDate start = DiscordianDate.of(startYear, startMonth, startDayOfMonth);
        DiscordianDate end = DiscordianDate.of(endYear, endMonth, endDayOfMonth);

        assertEquals(expected, start.until(end, unit));
    }
}
