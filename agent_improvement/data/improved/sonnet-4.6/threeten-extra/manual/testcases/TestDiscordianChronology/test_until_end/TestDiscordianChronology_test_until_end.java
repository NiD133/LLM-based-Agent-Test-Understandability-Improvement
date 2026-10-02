package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_until_end {

    // Each row: year1, month1, day1, year2, month2, day2, expectedYears, expectedMonths, expectedDays
    public static Object[][] data_until_period() {
        return new Object[][] {
            // Same date → zero period
            { 2014, 5, 26,   2014, 5, 26,   0,  0,   0 },

            // Day differences within a month
            { 2014, 5, 26,   2014, 5, 32,   0,  0,   6 },
            { 2014, 5, 26,   2014, 5, 20,   0,  0,  -6 },
            { 2014, 5, 26,   2014, 5, 30,   0,  0,   4 },
            { 2014, 5, 26,   2014, 5, 31,   0,  0,   5 },

            // Spanning into the next year, under one month
            { 2014, 5, 26,   2015, 1, 25,   0,  0,  72 },

            // Exactly one month forward
            { 2014, 5, 26,   2015, 1, 26,   0,  1,   0 },

            // Spanning several months, under one year
            { 2014, 5, 26,   2015, 5, 25,   0,  4,  72 },

            // Exactly one year forward
            { 2014, 5, 26,   2015, 5, 26,   1,  0,   0 },

            // Multi-year spans
            { 2014, 5, 26,   2024, 5, 25,   9,  4,  72 },
            { 2014, 5, 26,   2024, 5, 26,  10,  0,   0 },

            // St. Tib's Day (leap-day, month=0, day=0) interactions — forward
            { 2014, 1, 59,   2014, 1, 60,   0,  0,   2 },  // skipping over St. Tib's
            { 2014, 1, 59,   2014, 0,  0,   0,  0,   1 },  // landing on St. Tib's
            { 2014, 0,  0,   2014, 1, 60,   0,  0,   1 },  // starting on St. Tib's
            { 2014, 1, 60,   2014, 1, 55,   0,  0,  -6 },
            { 2014, 1, 60,   2014, 1, 59,   0,  0,  -2 },
            { 2014, 1, 60,   2014, 1, 55,   0,  0,  -6 },
            { 2014, 0,  0,   2014, 1, 54,   0,  0,  -6 },
            { 2014, 0,  0,   2014, 1, 65,   0,  0,   6 },
            { 2014, 1, 55,   2014, 0,  0,   0,  0,   5 },
            { 2014, 1, 64,   2014, 0,  0,   0,  0,  -5 },

            // St. Tib's Day — month boundary
            { 2014, 0,  0,   2014, 2, 59,   0,  0,  73 },
            { 2014, 0,  0,   2014, 2, 60,   0,  1,   0 },
            { 2014, 2, 60,   2014, 0,  0,   0, -1,  -1 },
            { 2014, 2, 59,   2014, 0,  0,   0,  0, -73 },
            { 2013, 5, 59,   2014, 0,  0,   0,  1,   1 },
            { 2013, 5, 60,   2014, 0,  0,   0,  0,  73 },
            { 2013, 5, 60,   2014, 1, 60,   0,  1,   0 },

            // St. Tib's Day — year boundary
            { 2014, 0,  0,   2015, 1, 59,   0,  4,  72 },
            { 2014, 0,  0,   2015, 1, 60,   1,  0,   0 },
            { 2013, 1, 60,   2014, 0,  0,   0,  4,  73 },
            { 2013, 1, 59,   2014, 0,  0,   1,  0,   1 },
            { 2013, 1, 60,   2014, 1, 60,   1,  0,   0 },

            // Negative (backwards) spans involving St. Tib's Day
            { 2014, 0,  0,   2013, 1, 59,  -1,  0,  -1 },
            { 2014, 0,  0,   2013, 1, 60,   0, -4, -73 },
            { 2015, 1, 60,   2014, 0,  0,  -1,  0,  -1 },
            { 2015, 1, 59,   2014, 0,  0,   0, -4, -73 },

            // Multi-year spans involving two St. Tib's Days
            { 2018, 0,  0,   2014, 0,  0,  -4,  0,   0 },
            { 2014, 0,  0,   2018, 0,  0,   4,  0,   0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until_period")
    public void test_until_end(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            int yearPeriod, int monthPeriod, int domPeriod) {
        DiscordianDate start = DiscordianDate.of(year1, month1, dom1);
        DiscordianDate end   = DiscordianDate.of(year2, month2, dom2);
        ChronoPeriod expected = DiscordianChronology.INSTANCE.period(yearPeriod, monthPeriod, domPeriod);
        assertEquals(expected, start.until(end));
    }
}
