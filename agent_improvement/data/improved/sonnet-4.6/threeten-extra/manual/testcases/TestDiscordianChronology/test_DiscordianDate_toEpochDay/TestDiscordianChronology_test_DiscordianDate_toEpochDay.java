package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class TestDiscordianChronology_test_DiscordianDate_toEpochDay {

    // Each entry maps a DiscordianDate to its equivalent ISO LocalDate.
    // Both should produce the same epoch-day value.
    public static Object[][] data_samples() {
        return new Object[][] {
            // Ancient / BCE dates
            { DiscordianDate.of(2,    1,  1), LocalDate.of(-1164,  1,  1) },
            { DiscordianDate.of(166,  1,  1), LocalDate.of(-1000,  1,  1) },
            { DiscordianDate.of(1156, 1,  1), LocalDate.of(  -10,  1,  1) },

            // Year boundary: YOLD 1166 = ISO year 0, YOLD 1167 = ISO year 1
            { DiscordianDate.of(1166, 1,  1), LocalDate.of(    0,  1,  1) },
            { DiscordianDate.of(1167, 1,  1), LocalDate.of(    1,  1,  1) },

            // Sequential days in early ISO year 1 (Chaos, days 1-3)
            { DiscordianDate.of(1167, 1,  2), LocalDate.of(    1,  1,  2) },
            { DiscordianDate.of(1167, 1,  3), LocalDate.of(    1,  1,  3) },

            // Days around the February/March boundary in a non-leap year (ISO year 1)
            // Day 57 of Chaos = Feb 26, day 58 = Feb 27, day 59 = Feb 28, day 60 = Mar 1
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(    1,  2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(    1,  2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(    1,  2, 28) },
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(    1,  3,  1) },

            // Days around the leap-day in a leap year (ISO year 4 / YOLD 1170)
            // St. Tib's Day (month=0, day=0) corresponds to Feb 29
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(    4,  2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(    4,  2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(    4,  2, 28) },
            { DiscordianDate.of(1170, 0,  0), LocalDate.of(    4,  2, 29) }, // St. Tib's Day
            { DiscordianDate.of(1170, 1, 60), LocalDate.of(    4,  3,  1) },

            // Days around the Feb/Mar boundary in ISO 100 (century year, NOT a leap year)
            // No St. Tib's Day: day 59 goes directly to day 60 / Mar 1
            { DiscordianDate.of(1266, 1, 57), LocalDate.of(  100,  2, 26) },
            { DiscordianDate.of(1266, 1, 58), LocalDate.of(  100,  2, 27) },
            { DiscordianDate.of(1266, 1, 59), LocalDate.of(  100,  2, 28) },
            { DiscordianDate.of(1266, 1, 60), LocalDate.of(  100,  3,  1) },
            { DiscordianDate.of(1266, 1, 61), LocalDate.of(  100,  3,  2) },

            // Last days of ISO year 0 (Dec 30 and Dec 31 fall in Aftermath, season 5)
            { DiscordianDate.of(1166, 5, 72), LocalDate.of(    0, 12, 30) },
            { DiscordianDate.of(1166, 5, 73), LocalDate.of(    0, 12, 31) },

            // Historically significant dates
            // Days around the Gregorian calendar reform (Oct 1582)
            { DiscordianDate.of(2748, 4, 68), LocalDate.of( 1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69), LocalDate.of( 1582, 10, 15) },

            // End of World War II (Nov 12, 1945)
            { DiscordianDate.of(3111, 5, 24), LocalDate.of( 1945, 11, 12) },

            // Two consecutive modern dates (Jul 5-6, 2012)
            { DiscordianDate.of(3178, 3, 40), LocalDate.of( 2012,  7,  5) },
            { DiscordianDate.of(3178, 3, 41), LocalDate.of( 2012,  7,  6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_toEpochDay(DiscordianDate discordian, LocalDate iso) {
        assertEquals(iso.toEpochDay(), discordian.toEpochDay());
    }
}
