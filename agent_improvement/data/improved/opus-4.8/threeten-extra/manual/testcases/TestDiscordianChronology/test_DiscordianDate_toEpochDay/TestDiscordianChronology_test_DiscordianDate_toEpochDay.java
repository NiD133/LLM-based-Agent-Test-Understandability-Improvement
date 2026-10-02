package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link DiscordianDate#toEpochDay()} agrees with the epoch day of the
 * equivalent ISO {@link LocalDate}.
 * <p>
 * Each sample pairs a Discordian date with the ISO date that falls on the same calendar
 * day. Since both calendars share the same epoch (1970-01-01), corresponding dates must
 * report the same epoch day.
 */
public class TestDiscordianChronology_test_DiscordianDate_toEpochDay {

    /**
     * Pairs of equivalent dates: a Discordian date and the ISO date for the same day.
     * The Discordian date is given as {year, month, dayOfMonth}, where month/day {0, 0}
     * denotes St. Tib's Day (the leap day that belongs to no Discordian month).
     */
    public static Object[][] data_equivalentDates() {
        return new Object[][] {
            // Early Discordian years (ISO BCE) up to the epoch alignment at YOLD 1166 / ISO 0
            { DiscordianDate.of(2, 1, 1),       LocalDate.of(-1164, 1, 1) },
            { DiscordianDate.of(166, 1, 1),     LocalDate.of(-1000, 1, 1) },
            { DiscordianDate.of(1156, 1, 1),    LocalDate.of(-10, 1, 1) },
            { DiscordianDate.of(1166, 1, 1),    LocalDate.of(0, 1, 1) },

            // Start of YOLD 1167 (ISO year 1) - first few days of the first month
            { DiscordianDate.of(1167, 1, 1),    LocalDate.of(1, 1, 1) },
            { DiscordianDate.of(1167, 1, 2),    LocalDate.of(1, 1, 2) },
            { DiscordianDate.of(1167, 1, 3),    LocalDate.of(1, 1, 3) },

            // Days around the end of ISO February in a non-leap year (no St. Tib's Day)
            { DiscordianDate.of(1167, 1, 57),   LocalDate.of(1, 2, 26) },
            { DiscordianDate.of(1167, 1, 58),   LocalDate.of(1, 2, 27) },
            { DiscordianDate.of(1167, 1, 59),   LocalDate.of(1, 2, 28) },
            { DiscordianDate.of(1167, 1, 60),   LocalDate.of(1, 3, 1) },

            // Leap year (YOLD 1170 / ISO 4): St. Tib's Day sits between day 59 and day 60
            { DiscordianDate.of(1170, 1, 57),   LocalDate.of(4, 2, 26) },
            { DiscordianDate.of(1170, 1, 58),   LocalDate.of(4, 2, 27) },
            { DiscordianDate.of(1170, 1, 59),   LocalDate.of(4, 2, 28) },
            { DiscordianDate.of(1170, 0, 0),    LocalDate.of(4, 2, 29) },  // St. Tib's Day
            { DiscordianDate.of(1170, 1, 60),   LocalDate.of(4, 3, 1) },

            // Century non-leap year (YOLD 1266 / ISO 100): ISO 100 is not a leap year
            { DiscordianDate.of(1266, 1, 57),   LocalDate.of(100, 2, 26) },
            { DiscordianDate.of(1266, 1, 58),   LocalDate.of(100, 2, 27) },
            { DiscordianDate.of(1266, 1, 59),   LocalDate.of(100, 2, 28) },
            { DiscordianDate.of(1266, 1, 60),   LocalDate.of(100, 3, 1) },
            { DiscordianDate.of(1266, 1, 61),   LocalDate.of(100, 3, 2) },

            // End of ISO year 0
            { DiscordianDate.of(1166, 5, 73),   LocalDate.of(0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72),   LocalDate.of(0, 12, 30) },

            // Gregorian calendar cutover region (October 1582) - proleptic, no gap here
            { DiscordianDate.of(2748, 4, 68),   LocalDate.of(1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69),   LocalDate.of(1582, 10, 15) },

            // Assorted modern dates
            { DiscordianDate.of(3111, 5, 24),   LocalDate.of(1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40),   LocalDate.of(2012, 7, 5) },
            { DiscordianDate.of(3178, 3, 41),   LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_equivalentDates")
    public void test_DiscordianDate_toEpochDay(DiscordianDate discordianDate, LocalDate equivalentIsoDate) {
        assertEquals(equivalentIsoDate.toEpochDay(), discordianDate.toEpochDay());
    }
}
