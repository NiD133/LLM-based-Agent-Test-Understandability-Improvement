package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_DiscordianDate_from_LocalDate {

    /**
     * Pairs of (expected DiscordianDate, ISO LocalDate) used to verify that
     * DiscordianDate.from(LocalDate) produces the correct Discordian date.
     *
     * The Discordian calendar year is ISO year + 1166. Each Discordian year has
     * five 73-day months. In leap years a special "St. Tib's Day" (month=0, day=0)
     * is inserted between day 59 and day 60 of the first month, matching the ISO
     * leap day (Feb 29).
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Ancient / negative ISO years ---
            { DiscordianDate.of(2,    1,  1), LocalDate.of(-1164,  1,  1) },
            { DiscordianDate.of(166,  1,  1), LocalDate.of(-1000,  1,  1) },
            { DiscordianDate.of(1156, 1,  1), LocalDate.of(  -10,  1,  1) },

            // --- ISO year 0 ---
            { DiscordianDate.of(1166, 1,  1), LocalDate.of(    0,  1,  1) },
            { DiscordianDate.of(1166, 5, 72), LocalDate.of(    0, 12, 30) },
            { DiscordianDate.of(1166, 5, 73), LocalDate.of(    0, 12, 31) },

            // --- ISO year 1: normal (non-leap) year ---
            { DiscordianDate.of(1167, 1,  1), LocalDate.of(    1,  1,  1) },
            { DiscordianDate.of(1167, 1,  2), LocalDate.of(    1,  1,  2) },
            { DiscordianDate.of(1167, 1,  3), LocalDate.of(    1,  1,  3) },
            // The Discordian month boundary falls mid-ISO-month; day 57 of month 1 = Feb 26
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(    1,  2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(    1,  2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(    1,  2, 28) },
            // Feb 28 in a non-leap year is the last day before day 60 (no St. Tib's Day)
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(    1,  3,  1) },

            // --- ISO year 4: leap year (contains St. Tib's Day) ---
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(    4,  2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(    4,  2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(    4,  2, 28) },
            // Feb 29 in a leap year maps to St. Tib's Day (month=0, day=0)
            { DiscordianDate.of(1170, 0,  0), LocalDate.of(    4,  2, 29) },
            // Mar 1 resumes as day 60 of month 1
            { DiscordianDate.of(1170, 1, 60), LocalDate.of(    4,  3,  1) },

            // --- ISO year 100: century year (not a leap year) ---
            { DiscordianDate.of(1266, 1, 57), LocalDate.of(  100,  2, 26) },
            { DiscordianDate.of(1266, 1, 58), LocalDate.of(  100,  2, 27) },
            { DiscordianDate.of(1266, 1, 59), LocalDate.of(  100,  2, 28) },
            // No leap day in a century year; Mar 1 is immediately day 60
            { DiscordianDate.of(1266, 1, 60), LocalDate.of(  100,  3,  1) },
            { DiscordianDate.of(1266, 1, 61), LocalDate.of(  100,  3,  2) },

            // --- Historical / well-known dates ---
            // Day before the Gregorian calendar reform (Julian calendar)
            { DiscordianDate.of(2748, 4, 68), LocalDate.of( 1582, 10, 14) },
            // First day of the Gregorian calendar
            { DiscordianDate.of(2748, 4, 69), LocalDate.of( 1582, 10, 15) },
            { DiscordianDate.of(3111, 5, 24), LocalDate.of( 1945, 11, 12) },

            // --- Two consecutive days in 2012 ---
            { DiscordianDate.of(3178, 3, 40), LocalDate.of( 2012,  7,  5) },
            { DiscordianDate.of(3178, 3, 41), LocalDate.of( 2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_from_LocalDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(discordian, DiscordianDate.from(iso));
    }
}
