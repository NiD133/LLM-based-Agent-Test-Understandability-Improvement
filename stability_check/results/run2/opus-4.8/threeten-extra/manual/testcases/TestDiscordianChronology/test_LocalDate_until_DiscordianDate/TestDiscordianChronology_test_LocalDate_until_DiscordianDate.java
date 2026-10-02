package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link LocalDate#until(java.time.temporal.Temporal)} returns a zero
 * period when the target {@link DiscordianDate} denotes the very same calendar day.
 * <p>
 * Each test case pairs a Discordian date with the ISO (proleptic Gregorian) date that
 * represents the identical point in time. Because both dates are the same day, the
 * elapsed period between them must be {@link Period#ZERO}.
 */
public class TestDiscordianChronology_test_LocalDate_until_DiscordianDate {

    /**
     * Provides equivalent (Discordian, ISO) date pairs that refer to the same day.
     * The pairs cover era boundaries, leap years (St. Tib's Day), month rollovers
     * and several historically notable dates.
     */
    public static Object[][] equivalentDatePairs() {
        return new Object[][] {
            { DiscordianDate.of(2, 1, 1),      LocalDate.of(-1164, 1, 1) },
            { DiscordianDate.of(166, 1, 1),    LocalDate.of(-1000, 1, 1) },
            { DiscordianDate.of(1156, 1, 1),   LocalDate.of(-10, 1, 1) },
            { DiscordianDate.of(1166, 1, 1),   LocalDate.of(0, 1, 1) },
            { DiscordianDate.of(1167, 1, 1),   LocalDate.of(1, 1, 1) },
            { DiscordianDate.of(1167, 1, 2),   LocalDate.of(1, 1, 2) },
            { DiscordianDate.of(1167, 1, 3),   LocalDate.of(1, 1, 3) },
            { DiscordianDate.of(1167, 1, 57),  LocalDate.of(1, 2, 26) },
            { DiscordianDate.of(1167, 1, 58),  LocalDate.of(1, 2, 27) },
            { DiscordianDate.of(1167, 1, 59),  LocalDate.of(1, 2, 28) },
            { DiscordianDate.of(1167, 1, 60),  LocalDate.of(1, 3, 1) },
            { DiscordianDate.of(1170, 1, 57),  LocalDate.of(4, 2, 26) },
            { DiscordianDate.of(1170, 1, 58),  LocalDate.of(4, 2, 27) },
            { DiscordianDate.of(1170, 1, 59),  LocalDate.of(4, 2, 28) },
            { DiscordianDate.of(1170, 0, 0),   LocalDate.of(4, 2, 29) },  // St. Tib's Day (leap)
            { DiscordianDate.of(1170, 1, 60),  LocalDate.of(4, 3, 1) },
            { DiscordianDate.of(1266, 1, 57),  LocalDate.of(100, 2, 26) },
            { DiscordianDate.of(1266, 1, 58),  LocalDate.of(100, 2, 27) },
            { DiscordianDate.of(1266, 1, 59),  LocalDate.of(100, 2, 28) },
            { DiscordianDate.of(1266, 1, 60),  LocalDate.of(100, 3, 1) },
            { DiscordianDate.of(1266, 1, 61),  LocalDate.of(100, 3, 2) },
            { DiscordianDate.of(1166, 5, 73),  LocalDate.of(0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72),  LocalDate.of(0, 12, 30) },
            { DiscordianDate.of(2748, 4, 68),  LocalDate.of(1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69),  LocalDate.of(1582, 10, 15) },
            { DiscordianDate.of(3111, 5, 24),  LocalDate.of(1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40),  LocalDate.of(2012, 7, 5) },
            { DiscordianDate.of(3178, 3, 41),  LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("equivalentDatePairs")
    public void until_sameDayDiscordianDate_isZeroPeriod(DiscordianDate sameDayDiscordian, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(sameDayDiscordian));
    }
}
