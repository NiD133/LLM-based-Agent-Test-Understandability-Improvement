package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_range {

    // 2010 is a Discordian leap year (ISO 844); 2011 is not.
    // St. Tib's Day is represented as month=0, day=0.

    public static Object[][] data_ranges() {
        return new Object[][] {
            // --- DAY_OF_MONTH ---
            // St. Tib's Day occupies its own "month 0", so its only valid day-of-month is 0.
            { 2010, 0,  0, DAY_OF_MONTH, 0, 0  },
            // Regular Discordian months have 73 days (1–73).
            { 2010, 1, 23, DAY_OF_MONTH, 1, 73 },
            { 2010, 2, 23, DAY_OF_MONTH, 1, 73 },
            { 2010, 3, 23, DAY_OF_MONTH, 1, 73 },
            { 2010, 4, 23, DAY_OF_MONTH, 1, 73 },
            { 2010, 5, 23, DAY_OF_MONTH, 1, 73 },

            // --- DAY_OF_YEAR ---
            // Leap year: days are numbered 1–366 (St. Tib's Day is included in the ordinal count).
            { 2010, 0,  0, DAY_OF_YEAR, 1, 366 },
            { 2010, 1, 23, DAY_OF_YEAR, 1, 366 },
            // Non-leap year: days are numbered 1–365.
            { 2011, 2, 23, DAY_OF_YEAR, 1, 365 },

            // --- MONTH_OF_YEAR ---
            // In a leap year St. Tib's Day is present, so months range from 0 (St. Tib's) to 5.
            { 2010, 0, 0, MONTH_OF_YEAR, 0, 5 },
            { 2010, 1, 1, MONTH_OF_YEAR, 0, 5 },
            // In a non-leap year there is no St. Tib's Day, so months range from 1 to 5.
            { 2011, 1, 23, MONTH_OF_YEAR, 1, 5 },

            // --- ALIGNED_DAY_OF_WEEK_IN_MONTH ---
            // St. Tib's Day is not part of any week, so its aligned-day-of-week-in-month is always 0.
            { 2010, 0,  0, ALIGNED_DAY_OF_WEEK_IN_MONTH, 0, 0 },
            // Regular days: 5-day Discordian weeks → values 1–5.
            { 2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5 },
            { 2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5 },
            { 2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 5 },

            // --- ALIGNED_DAY_OF_WEEK_IN_YEAR ---
            // In a leap year St. Tib's Day shifts the range to 0–5.
            { 2010, 0,  0, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5 },
            { 2010, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5 },
            { 2010, 1, 59, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5 },
            { 2010, 1, 60, ALIGNED_DAY_OF_WEEK_IN_YEAR, 0, 5 },
            // In a non-leap year every day belongs to a regular week: values 1–5.
            { 2011, 1, 23, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 5 },

            // --- ALIGNED_WEEK_OF_MONTH ---
            // St. Tib's Day belongs to week 0 (its own special week).
            { 2010, 0,  0, ALIGNED_WEEK_OF_MONTH, 0,  0  },
            // Regular months have 73 days / 5-day weeks = 14 full weeks + remainder → 1–15.
            { 2010, 1, 23, ALIGNED_WEEK_OF_MONTH, 1, 15 },

            // --- ALIGNED_WEEK_OF_YEAR ---
            // In a leap year St. Tib's Day is week 0; the 73 regular weeks are 0–73.
            { 2010, 0,  0, ALIGNED_WEEK_OF_YEAR, 0, 73 },
            { 2010, 1, 23, ALIGNED_WEEK_OF_YEAR, 0, 73 },
            // In a non-leap year there is no week 0: weeks are numbered 1–73.
            { 2011, 1, 23, ALIGNED_WEEK_OF_YEAR, 1, 73 },

            // --- DAY_OF_WEEK ---
            // St. Tib's Day is outside the Discordian week, so its day-of-week is always 0.
            { 2010, 0, 0, DAY_OF_WEEK, 0, 0 },
            // Regular days have a day-of-week in the range 1–5.
            { 2010, 1, 1, DAY_OF_WEEK, 1, 5 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        assertEquals(ValueRange.of(expectedMin, expectedMax), DiscordianDate.of(year, month, dom).range(field));
    }
}
