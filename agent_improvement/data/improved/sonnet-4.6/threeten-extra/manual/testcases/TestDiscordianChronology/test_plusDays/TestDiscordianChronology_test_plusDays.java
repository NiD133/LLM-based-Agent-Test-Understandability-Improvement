package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that DiscordianDate.plus(n, DAYS) correctly advances a date by n days,
 * verified by comparing against the equivalent ISO LocalDate arithmetic.
 */
public class TestDiscordianChronology_test_plusDays {

    /**
     * Pairs of (DiscordianDate, equivalent ISO LocalDate) covering key boundary cases:
     * early years, normal months, St. Tib's Day (leap day, month=0 day=0),
     * century years (leap / non-leap), and dates around the Gregorian calendar reform.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { DiscordianDate.of(2, 1, 1),     LocalDate.of(-1164,  1,  1) },
            { DiscordianDate.of(166, 1, 1),   LocalDate.of(-1000,  1,  1) },
            { DiscordianDate.of(1156, 1, 1),  LocalDate.of(  -10,  1,  1) },
            { DiscordianDate.of(1166, 1, 1),  LocalDate.of(    0,  1,  1) },
            { DiscordianDate.of(1167, 1, 1),  LocalDate.of(    1,  1,  1) },
            { DiscordianDate.of(1167, 1, 2),  LocalDate.of(    1,  1,  2) },
            { DiscordianDate.of(1167, 1, 3),  LocalDate.of(    1,  1,  3) },
            // boundary around end of first Discordian month in a non-leap year
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(    1,  2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(    1,  2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(    1,  2, 28) },
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(    1,  3,  1) },
            // boundary around St. Tib's Day (leap day) in year 1170 (ISO 4)
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(    4,  2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(    4,  2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(    4,  2, 28) },
            { DiscordianDate.of(1170, 0,  0), LocalDate.of(    4,  2, 29) }, // St. Tib's Day
            { DiscordianDate.of(1170, 1, 60), LocalDate.of(    4,  3,  1) },
            // century year that is NOT a leap year (ISO 100)
            { DiscordianDate.of(1266, 1, 57), LocalDate.of(  100,  2, 26) },
            { DiscordianDate.of(1266, 1, 58), LocalDate.of(  100,  2, 27) },
            { DiscordianDate.of(1266, 1, 59), LocalDate.of(  100,  2, 28) },
            { DiscordianDate.of(1266, 1, 60), LocalDate.of(  100,  3,  1) },
            { DiscordianDate.of(1266, 1, 61), LocalDate.of(  100,  3,  2) },
            // end of Discordian year 1166 (ISO year 0)
            { DiscordianDate.of(1166, 5, 73), LocalDate.of(    0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72), LocalDate.of(    0, 12, 30) },
            // dates spanning the Gregorian calendar reform (Oct 1582)
            { DiscordianDate.of(2748, 4, 68), LocalDate.of( 1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69), LocalDate.of( 1582, 10, 15) },
            // miscellaneous modern dates
            { DiscordianDate.of(3111, 5, 24), LocalDate.of( 1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40), LocalDate.of( 2012,  7,  5) },
            { DiscordianDate.of(3178, 3, 41), LocalDate.of( 2012,  7,  6) },
        };
    }

    /**
     * Verifies that adding 0, +1, +35, -1, and -60 days to a DiscordianDate
     * yields the same calendar date as performing the equivalent arithmetic on
     * the ISO LocalDate counterpart.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(DiscordianDate discordian, LocalDate iso) {
        assertEquals(iso,              LocalDate.from(discordian.plus(  0, DAYS)));
        assertEquals(iso.plusDays( 1), LocalDate.from(discordian.plus(  1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(discordian.plus( 35, DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(discordian.plus( -1, DAYS)));
        assertEquals(iso.plusDays(-60),LocalDate.from(discordian.plus(-60, DAYS)));
    }
}
