package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that DiscordianDate.until(temporal, DAYS) correctly computes the
 * signed number of days between a Discordian date and a target ISO date.
 *
 * Each data_samples row supplies a (DiscordianDate, LocalDate) pair that
 * represents the same calendar day, so the difference must be exactly
 * the number of days added/subtracted from the ISO anchor.
 */
public class TestDiscordianChronology_test_until_DAYS {

    // Each row: a Discordian date and the equivalent ISO date (same calendar day).
    public static Object[][] data_samples() {
        return new Object[][] {
            { DiscordianDate.of(2, 1, 1),      LocalDate.of(-1164,  1,  1) },
            { DiscordianDate.of(166, 1, 1),    LocalDate.of(-1000,  1,  1) },
            { DiscordianDate.of(1156, 1, 1),   LocalDate.of(  -10,  1,  1) },
            { DiscordianDate.of(1166, 1, 1),   LocalDate.of(    0,  1,  1) },
            { DiscordianDate.of(1167, 1, 1),   LocalDate.of(    1,  1,  1) },
            { DiscordianDate.of(1167, 1, 2),   LocalDate.of(    1,  1,  2) },
            { DiscordianDate.of(1167, 1, 3),   LocalDate.of(    1,  1,  3) },
            { DiscordianDate.of(1167, 1, 57),  LocalDate.of(    1,  2, 26) },
            { DiscordianDate.of(1167, 1, 58),  LocalDate.of(    1,  2, 27) },
            { DiscordianDate.of(1167, 1, 59),  LocalDate.of(    1,  2, 28) },
            { DiscordianDate.of(1167, 1, 60),  LocalDate.of(    1,  3,  1) },
            { DiscordianDate.of(1170, 1, 57),  LocalDate.of(    4,  2, 26) },
            { DiscordianDate.of(1170, 1, 58),  LocalDate.of(    4,  2, 27) },
            { DiscordianDate.of(1170, 1, 59),  LocalDate.of(    4,  2, 28) },
            // St. Tib's Day (Discordian leap day) corresponds to ISO Feb 29
            { DiscordianDate.of(1170, 0, 0),   LocalDate.of(    4,  2, 29) },
            { DiscordianDate.of(1170, 1, 60),  LocalDate.of(    4,  3,  1) },
            { DiscordianDate.of(1266, 1, 57),  LocalDate.of(  100,  2, 26) },
            { DiscordianDate.of(1266, 1, 58),  LocalDate.of(  100,  2, 27) },
            { DiscordianDate.of(1266, 1, 59),  LocalDate.of(  100,  2, 28) },
            // ISO year 100 is NOT a leap year, so no St. Tib's Day; day 59 is followed by day 60
            { DiscordianDate.of(1266, 1, 60),  LocalDate.of(  100,  3,  1) },
            { DiscordianDate.of(1266, 1, 61),  LocalDate.of(  100,  3,  2) },
            { DiscordianDate.of(1166, 5, 73),  LocalDate.of(    0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72),  LocalDate.of(    0, 12, 30) },
            { DiscordianDate.of(2748, 4, 68),  LocalDate.of( 1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69),  LocalDate.of( 1582, 10, 15) },
            { DiscordianDate.of(3111, 5, 24),  LocalDate.of( 1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40),  LocalDate.of( 2012,  7,  5) },
            { DiscordianDate.of(3178, 3, 41),  LocalDate.of( 2012,  7,  6) },
        };
    }

    /**
     * Verifies that until(target, DAYS) returns the correct signed day count
     * for several representative offsets relative to the anchor ISO date.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(DiscordianDate discordianAnchor, LocalDate isoAnchor) {
        assertEquals(  0, discordianAnchor.until(isoAnchor.plusDays( 0), DAYS));
        assertEquals(  1, discordianAnchor.until(isoAnchor.plusDays( 1), DAYS));
        assertEquals( 35, discordianAnchor.until(isoAnchor.plusDays(35), DAYS));
        assertEquals(-40, discordianAnchor.until(isoAnchor.minusDays(40), DAYS));
    }
}
