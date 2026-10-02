package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link DiscordianDate#until(java.time.chrono.ChronoLocalDate)}, which returns the
 * period (years, months, days) elapsed between two Discordian dates.
 * <p>
 * In the Discordian calendar a date is written as (year, month, day-of-month) where months
 * run 1..5 and days 1..73. The special value (year, 0, 0) denotes St. Tib's Day, a day that
 * belongs to no month and is inserted in leap years.
 */
public class TestDiscordianChronology_test_until_end {

    /**
     * Test cases for {@link #test_until_end}.
     * <p>
     * Each row is: {@code startYear, startMonth, startDay, endYear, endMonth, endDay,
     * expectedYears, expectedMonths, expectedDays} — the start date, the end date, and the
     * year/month/day components of the period that {@code start.until(end)} should yield.
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            // Same date and small intraday differences within a single month.
            { 2014, 5, 26, 2014, 5, 26, 0, 0, 0 },
            { 2014, 5, 26, 2014, 5, 32, 0, 0, 6 },
            { 2014, 5, 26, 2014, 5, 20, 0, 0, -6 },
            { 2014, 5, 26, 2014, 5, 30, 0, 0, 4 },
            { 2014, 5, 26, 2014, 5, 31, 0, 0, 5 },
            // Crossing month and year boundaries.
            { 2014, 5, 26, 2015, 1, 25, 0, 0, 72 },
            { 2014, 5, 26, 2015, 1, 26, 0, 1, 0 },
            { 2014, 5, 26, 2015, 5, 25, 0, 4, 72 },
            { 2014, 5, 26, 2015, 5, 26, 1, 0, 0 },
            { 2014, 5, 26, 2024, 5, 25, 9, 4, 72 },
            { 2014, 5, 26, 2024, 5, 26, 10, 0, 0 },
            // Differences spanning St. Tib's Day (year, 0, 0).
            { 2014, 1, 59, 2014, 1, 60, 0, 0, 2 },
            { 2014, 1, 59, 2014, 0, 0, 0, 0, 1 },
            { 2014, 0, 0, 2014, 1, 60, 0, 0, 1 },
            { 2014, 1, 60, 2014, 1, 55, 0, 0, -6 },
            { 2014, 1, 60, 2014, 1, 59, 0, 0, -2 },
            { 2014, 1, 60, 2014, 1, 55, 0, 0, -6 },
            { 2014, 0, 0, 2014, 1, 54, 0, 0, -6 },
            { 2014, 0, 0, 2014, 1, 65, 0, 0, 6 },
            { 2014, 1, 55, 2014, 0, 0, 0, 0, 5 },
            { 2014, 1, 64, 2014, 0, 0, 0, 0, -5 },
            { 2014, 0, 0, 2014, 2, 59, 0, 0, 73 },
            { 2014, 0, 0, 2014, 2, 60, 0, 1, 0 },
            { 2014, 2, 60, 2014, 0, 0, 0, -1, -1 },
            { 2014, 2, 59, 2014, 0, 0, 0, 0, -73 },
            { 2013, 5, 59, 2014, 0, 0, 0, 1, 1 },
            { 2013, 5, 60, 2014, 0, 0, 0, 0, 73 },
            { 2013, 5, 60, 2014, 1, 60, 0, 1, 0 },
            { 2014, 0, 0, 2015, 1, 59, 0, 4, 72 },
            { 2014, 0, 0, 2015, 1, 60, 1, 0, 0 },
            { 2013, 1, 60, 2014, 0, 0, 0, 4, 73 },
            { 2013, 1, 59, 2014, 0, 0, 1, 0, 1 },
            { 2013, 1, 60, 2014, 1, 60, 1, 0, 0 },
            { 2014, 0, 0, 2013, 1, 59, -1, 0, -1 },
            { 2014, 0, 0, 2013, 1, 60, 0, -4, -73 },
            { 2015, 1, 60, 2014, 0, 0, -1, 0, -1 },
            { 2015, 1, 59, 2014, 0, 0, 0, -4, -73 },
            { 2018, 0, 0, 2014, 0, 0, -4, 0, 0 },
            { 2014, 0, 0, 2018, 0, 0, 4, 0, 0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(int startYear, int startMonth, int startDay,
            int endYear, int endMonth, int endDay,
            int expectedYears, int expectedMonths, int expectedDays) {
        DiscordianDate start = DiscordianDate.of(startYear, startMonth, startDay);
        DiscordianDate end = DiscordianDate.of(endYear, endMonth, endDay);

        ChronoPeriod expectedPeriod =
                DiscordianChronology.INSTANCE.period(expectedYears, expectedMonths, expectedDays);

        assertEquals(expectedPeriod, start.until(end));
    }
}
