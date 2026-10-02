package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link DiscordianDate#until(java.time.chrono.ChronoLocalDate)} returns a
 * zero period when the Discordian date and the ISO {@link LocalDate} refer to the same day.
 */
public class TestDiscordianChronology_test_DiscordianDate_until_LocalDate {

    /**
     * Each row pairs a Discordian date with the equivalent ISO date for the very same day.
     * Because the two arguments denote the same calendar day, {@code until} must always
     * report no elapsed time between them.
     */
    public static Object[][] data_equivalentDiscordianAndIsoDates() {
        return new Object[][] {
            { DiscordianDate.of(2, 1, 1), LocalDate.of(-1164, 1, 1) },
            { DiscordianDate.of(166, 1, 1), LocalDate.of(-1000, 1, 1) },
            { DiscordianDate.of(1156, 1, 1), LocalDate.of(-10, 1, 1) },
            { DiscordianDate.of(1166, 1, 1), LocalDate.of(0, 1, 1) },
            { DiscordianDate.of(1167, 1, 1), LocalDate.of(1, 1, 1) },
            { DiscordianDate.of(1167, 1, 2), LocalDate.of(1, 1, 2) },
            { DiscordianDate.of(1167, 1, 3), LocalDate.of(1, 1, 3) },
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(1, 2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(1, 2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(1, 2, 28) },
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(1, 3, 1) },
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(4, 2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(4, 2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(4, 2, 28) },
            { DiscordianDate.of(1170, 0, 0), LocalDate.of(4, 2, 29) },
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
            { DiscordianDate.of(3178, 3, 41), LocalDate.of(2012, 7, 6) }
        };
    }

    @ParameterizedTest
    @MethodSource("data_equivalentDiscordianAndIsoDates")
    public void until_sameDayInIsoCalendar_returnsZeroPeriod(DiscordianDate discordian, LocalDate sameDayInIso) {
        ChronoPeriod zeroPeriod = DiscordianChronology.INSTANCE.period(0, 0, 0);
        assertEquals(zeroPeriod, discordian.until(sameDayInIso));
    }
}
