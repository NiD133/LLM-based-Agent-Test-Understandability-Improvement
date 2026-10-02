package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link LocalDate#until(java.time.chrono.ChronoLocalDate)} returns an empty
 * period when the target {@link DiscordianDate} denotes the very same calendar day.
 * <p>
 * Each row below pairs a Discordian date with the ISO date it should map to. Because both
 * dates point at the same instant on the timeline, the distance between them is always
 * {@link Period#ZERO}.
 */
public class TestDiscordianChronology_test_LocalDate_until_DiscordianDate {

    /**
     * Equivalent Discordian / ISO date pairs, i.e. two spellings of one and the same day.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { DiscordianDate.of(2, 1, 1),     LocalDate.of(-1164, 1, 1) },
            { DiscordianDate.of(166, 1, 1),   LocalDate.of(-1000, 1, 1) },
            { DiscordianDate.of(1156, 1, 1),  LocalDate.of(-10, 1, 1) },
            { DiscordianDate.of(1166, 1, 1),  LocalDate.of(0, 1, 1) },
            { DiscordianDate.of(1167, 1, 1),  LocalDate.of(1, 1, 1) },
            { DiscordianDate.of(1167, 1, 2),  LocalDate.of(1, 1, 2) },
            { DiscordianDate.of(1167, 1, 3),  LocalDate.of(1, 1, 3) },
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(1, 2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(1, 2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(1, 2, 28) },
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(1, 3, 1) },
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(4, 2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(4, 2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(4, 2, 28) },
            // St. Tib's Day (month 0, day 0) maps to the ISO leap day, Feb 29th.
            { DiscordianDate.of(1170, 0, 0),  LocalDate.of(4, 2, 29) },
            { DiscordianDate.of(1170, 1, 60), LocalDate.of(4, 3, 1) },
            { DiscordianDate.of(1266, 1, 57), LocalDate.of(100, 2, 26) },
            { DiscordianDate.of(1266, 1, 58), LocalDate.of(100, 2, 27) },
            { DiscordianDate.of(1266, 1, 59), LocalDate.of(100, 2, 28) },
            { DiscordianDate.of(1266, 1, 60), LocalDate.of(100, 3, 1) },
            { DiscordianDate.of(1266, 1, 61), LocalDate.of(100, 3, 2) },
            { DiscordianDate.of(1166, 5, 73), LocalDate.of(0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72), LocalDate.of(0, 12, 30) },
            { DiscordianDate.of(2748, 4, 68), LocalDate.of(1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69), LocalDate.of(1582, 10, 15) },
            { DiscordianDate.of(3111, 5, 24), LocalDate.of(1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40), LocalDate.of(2012, 7, 5) },
            { DiscordianDate.of(3178, 3, 41), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_DiscordianDate(DiscordianDate discordian, LocalDate iso) {
        // Same day expressed in two calendars, so no time elapses between them.
        assertEquals(Period.ZERO, iso.until(discordian));
    }
}
