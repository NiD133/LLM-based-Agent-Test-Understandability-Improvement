package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_until_end {

    public static Object[][] data_until_period() {
        return new Object[][] {
                { 2014, 5, 26, 2014, 5, 26, 0, 0, 0 },
                { 2014, 5, 26, 2014, 5, 32, 0, 0, 6 },
                { 2014, 5, 26, 2014, 5, 20, 0, 0, -6 },
                { 2014, 5, 26, 2014, 5, 30, 0, 0, 4 },
                { 2014, 5, 26, 2014, 5, 31, 0, 0, 5 },
                { 2014, 5, 26, 2015, 1, 25, 0, 0, 72 },
                { 2014, 5, 26, 2015, 1, 26, 0, 1, 0 },
                { 2014, 5, 26, 2015, 5, 25, 0, 4, 72 },
                { 2014, 5, 26, 2015, 5, 26, 1, 0, 0 },
                { 2014, 5, 26, 2024, 5, 25, 9, 4, 72 },
                { 2014, 5, 26, 2024, 5, 26, 10, 0, 0 },

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
    public void test_until_end(
            int startYear,
            int startMonth,
            int startDayOfMonth,
            int endYear,
            int endMonth,
            int endDayOfMonth,
            int expectedYears,
            int expectedMonths,
            int expectedDays) {

        DiscordianDate start = discordianDate(startYear, startMonth, startDayOfMonth);
        DiscordianDate end = discordianDate(endYear, endMonth, endDayOfMonth);
        ChronoPeriod expectedPeriod = discordianPeriod(expectedYears, expectedMonths, expectedDays);

        assertEquals(expectedPeriod, start.until(end));
    }

    private static DiscordianDate discordianDate(int year, int month, int dayOfMonth) {
        return DiscordianDate.of(year, month, dayOfMonth);
    }

    private static ChronoPeriod discordianPeriod(int years, int months, int days) {
        return DiscordianChronology.INSTANCE.period(years, months, days);
    }
}
