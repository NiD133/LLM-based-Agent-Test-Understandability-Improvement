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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link DiscordianDate#until(java.time.temporal.Temporal, TemporalUnit)}: the amount of
 * time, measured in a single {@link TemporalUnit}, between two Discordian dates.
 * <p>
 * Each test case is laid out as:
 * {@code { startYear, startMonth, startDom, endYear, endMonth, endDom, unit, expectedAmount }}.
 * <p>
 * A Discordian month {@code (0, 0)} denotes St. Tib's Day, the leap day that sits outside of any
 * month and week, so several cases below probe how {@code until} behaves around it.
 */
public class TestDiscordianChronology_test_until_TemporalUnit {

    public static Object[][] data_until() {
        return new Object[][] {
            // Same date is always zero distance, and the result is symmetric for a swapped range.
            { 2014, 5, 26, 2014, 5, 26, DAYS, 0 },
            { 2014, 5, 26, 2014, 5, 32, DAYS, 6 },
            { 2014, 5, 26, 2014, 5, 20, DAYS, -6 },

            // Whole units are truncated towards zero: a partial week/month/etc. counts as zero.
            { 2014, 5, 26, 2014, 5, 26, WEEKS, 0 },
            { 2014, 5, 26, 2014, 5, 30, WEEKS, 0 },
            { 2014, 5, 26, 2014, 5, 31, WEEKS, 1 },
            { 2014, 5, 26, 2014, 5, 26, MONTHS, 0 },
            { 2014, 5, 26, 2015, 1, 25, MONTHS, 0 },
            { 2014, 5, 26, 2015, 1, 26, MONTHS, 1 },
            { 2014, 5, 26, 2014, 5, 26, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 25, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 26, YEARS, 1 },
            { 2014, 5, 26, 2014, 5, 26, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 25, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 26, DECADES, 1 },
            { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 },
            { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 },

            // There is only one Discordian era, so any two dates are zero eras apart.
            { 2013, 5, 26, 3014, 5, 26, ERAS, 0 },

            // St. Tib's Day (month 0, dom 0) in days: it falls between dom 59 and dom 60 of month 1.
            { 2014, 1, 59, 2014, 1, 60, DAYS, 2 },
            { 2014, 1, 59, 2014, 0, 0, DAYS, 1 },
            { 2014, 0, 0, 2014, 1, 60, DAYS, 1 },
            { 2014, 1, 60, 2014, 1, 55, DAYS, -6 },

            // St. Tib's Day in weeks: it is outside the week structure, so it does not advance a week.
            { 2014, 0, 0, 2014, 0, 0, WEEKS, 0 },
            { 2014, 1, 60, 2014, 1, 60, WEEKS, 0 },
            { 2014, 1, 60, 2014, 1, 59, WEEKS, 0 },
            { 2014, 1, 60, 2014, 1, 56, WEEKS, 0 },
            { 2014, 1, 60, 2014, 1, 55, WEEKS, -1 },
            { 2014, 0, 0, 2014, 1, 54, WEEKS, -1 },
            { 2014, 0, 0, 2014, 1, 55, WEEKS, 0 },
            { 2014, 0, 0, 2014, 1, 64, WEEKS, 0 },
            { 2014, 0, 0, 2014, 1, 65, WEEKS, 1 },
            { 2014, 1, 54, 2014, 0, 0, WEEKS, 1 },
            { 2014, 1, 55, 2014, 0, 0, WEEKS, 0 },
            { 2014, 1, 64, 2014, 0, 0, WEEKS, 0 },
            { 2014, 1, 65, 2014, 0, 0, WEEKS, -1 },

            // St. Tib's Day in months: it counts as part of the first month for month arithmetic.
            { 2014, 0, 0, 2014, 0, 0, MONTHS, 0 },
            { 2014, 0, 0, 2014, 2, 59, MONTHS, 0 },
            { 2014, 0, 0, 2014, 2, 60, MONTHS, 1 },
            { 2014, 2, 60, 2014, 0, 0, MONTHS, -1 },
            { 2014, 2, 59, 2014, 0, 0, MONTHS, 0 },
            { 2013, 5, 59, 2014, 0, 0, MONTHS, 1 },
            { 2013, 5, 60, 2014, 0, 0, MONTHS, 0 },
            { 2013, 5, 60, 2014, 1, 60, MONTHS, 1 },

            // St. Tib's Day in years: it counts as part of its own year for year arithmetic.
            { 2014, 0, 0, 2014, 0, 0, YEARS, 0 },
            { 2014, 0, 0, 2015, 1, 59, YEARS, 0 },
            { 2014, 0, 0, 2015, 1, 60, YEARS, 1 },
            { 2013, 1, 60, 2014, 0, 0, YEARS, 0 },
            { 2013, 1, 59, 2014, 0, 0, YEARS, 1 },
            { 2013, 1, 60, 2014, 1, 60, YEARS, 1 },
            { 2014, 0, 0, 2013, 1, 59, YEARS, -1 },
            { 2014, 0, 0, 2013, 1, 60, YEARS, 0 },
            { 2015, 1, 60, 2014, 0, 0, YEARS, -1 },
            { 2015, 1, 59, 2014, 0, 0, YEARS, 0 },
            { 2018, 0, 0, 2014, 0, 0, YEARS, -4 },
            { 2014, 0, 0, 2018, 0, 0, YEARS, 4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(int startYear, int startMonth, int startDom,
            int endYear, int endMonth, int endDom, TemporalUnit unit, long expectedAmount) {
        DiscordianDate start = DiscordianDate.of(startYear, startMonth, startDom);
        DiscordianDate end = DiscordianDate.of(endYear, endMonth, endDom);

        assertEquals(expectedAmount, start.until(end, unit));
    }
}
