package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link LocalDate#until(java.time.chrono.ChronoLocalDate)} returns a
 * zero period when the target {@link DiscordianDate} denotes the very same calendar
 * day as the ISO {@code LocalDate} it was derived from.
 */
public class TestDiscordianChronology_test_LocalDate_until_DiscordianDate {

    /**
     * Each row pairs a {@link DiscordianDate} with the ISO {@link LocalDate} that
     * represents the identical day. The Discordian year is the ISO year + 1166, and
     * a Discordian "date" is given as (year, season, dayOfSeason); the special
     * (year, 0, 0) form denotes St. Tib's Day, which only exists in leap years.
     */
    public static Object[][] data_equivalentDates() {
        return new Object[][] {
            // Far-past dates, anchoring the year offset (Discordian year = ISO year + 1166).
            { DiscordianDate.of(2, 1, 1),       LocalDate.of(-1164, 1, 1) },
            { DiscordianDate.of(166, 1, 1),     LocalDate.of(-1000, 1, 1) },
            { DiscordianDate.of(1156, 1, 1),    LocalDate.of(-10, 1, 1) },
            { DiscordianDate.of(1166, 1, 1),    LocalDate.of(0, 1, 1) },

            // Start of the first ISO year, day by day.
            { DiscordianDate.of(1167, 1, 1),    LocalDate.of(1, 1, 1) },
            { DiscordianDate.of(1167, 1, 2),    LocalDate.of(1, 1, 2) },
            { DiscordianDate.of(1167, 1, 3),    LocalDate.of(1, 1, 3) },

            // Crossing the ISO Feb/Mar boundary in a non-leap year (no St. Tib's Day).
            { DiscordianDate.of(1167, 1, 57),   LocalDate.of(1, 2, 26) },
            { DiscordianDate.of(1167, 1, 58),   LocalDate.of(1, 2, 27) },
            { DiscordianDate.of(1167, 1, 59),   LocalDate.of(1, 2, 28) },
            { DiscordianDate.of(1167, 1, 60),   LocalDate.of(1, 3, 1) },

            // Leap year: St. Tib's Day (0, 0) maps to Feb 29.
            { DiscordianDate.of(1170, 1, 57),   LocalDate.of(4, 2, 26) },
            { DiscordianDate.of(1170, 1, 58),   LocalDate.of(4, 2, 27) },
            { DiscordianDate.of(1170, 1, 59),   LocalDate.of(4, 2, 28) },
            { DiscordianDate.of(1170, 0, 0),    LocalDate.of(4, 2, 29) },
            { DiscordianDate.of(1170, 1, 60),   LocalDate.of(4, 3, 1) },

            // Century year that is NOT a leap year (year 100): no St. Tib's Day.
            { DiscordianDate.of(1266, 1, 57),   LocalDate.of(100, 2, 26) },
            { DiscordianDate.of(1266, 1, 58),   LocalDate.of(100, 2, 27) },
            { DiscordianDate.of(1266, 1, 59),   LocalDate.of(100, 2, 28) },
            { DiscordianDate.of(1266, 1, 60),   LocalDate.of(100, 3, 1) },
            { DiscordianDate.of(1266, 1, 61),   LocalDate.of(100, 3, 2) },

            // End of an ISO year.
            { DiscordianDate.of(1166, 5, 73),   LocalDate.of(0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72),   LocalDate.of(0, 12, 30) },

            // Notable historical dates.
            { DiscordianDate.of(2748, 4, 68),   LocalDate.of(1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69),   LocalDate.of(1582, 10, 15) },
            { DiscordianDate.of(3111, 5, 24),   LocalDate.of(1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40),   LocalDate.of(2012, 7, 5) },
            { DiscordianDate.of(3178, 3, 41),   LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_equivalentDates")
    public void localDate_until_equivalentDiscordianDate_isZeroPeriod(DiscordianDate discordian, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(discordian));
    }
}
