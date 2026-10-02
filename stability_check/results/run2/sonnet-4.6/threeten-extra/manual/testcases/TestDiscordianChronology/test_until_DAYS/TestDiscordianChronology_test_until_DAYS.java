package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that DiscordianDate.until() correctly counts whole days between a
 * Discordian date and an ISO LocalDate, covering the full range of calendar
 * correspondences including leap years and the St. Tib's Day intercalation.
 */
public class TestDiscordianChronology_test_until_DAYS {

    public static Object[][] data_samples() {
        return new Object[][] {
            { DiscordianDate.of(2,    1,  1), LocalDate.of(-1164,  1,  1) },
            { DiscordianDate.of(166,  1,  1), LocalDate.of(-1000,  1,  1) },
            { DiscordianDate.of(1156, 1,  1), LocalDate.of(  -10,  1,  1) },
            { DiscordianDate.of(1166, 1,  1), LocalDate.of(    0,  1,  1) },
            { DiscordianDate.of(1167, 1,  1), LocalDate.of(    1,  1,  1) },
            { DiscordianDate.of(1167, 1,  2), LocalDate.of(    1,  1,  2) },
            { DiscordianDate.of(1167, 1,  3), LocalDate.of(    1,  1,  3) },
            // Last days of first Discordian month map onto late-February ISO dates
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(    1,  2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(    1,  2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(    1,  2, 28) },
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(    1,  3,  1) },
            // Same boundary in a leap year (ISO 4 AD); St. Tib's Day is inserted between day 59 and 60
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(    4,  2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(    4,  2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(    4,  2, 28) },
            { DiscordianDate.of(1170, 0,  0), LocalDate.of(    4,  2, 29) }, // St. Tib's Day
            { DiscordianDate.of(1170, 1, 60), LocalDate.of(    4,  3,  1) },
            // Non-leap centennial year (ISO 100 AD) — Feb 29 does not exist
            { DiscordianDate.of(1266, 1, 57), LocalDate.of(  100,  2, 26) },
            { DiscordianDate.of(1266, 1, 58), LocalDate.of(  100,  2, 27) },
            { DiscordianDate.of(1266, 1, 59), LocalDate.of(  100,  2, 28) },
            { DiscordianDate.of(1266, 1, 60), LocalDate.of(  100,  3,  1) },
            { DiscordianDate.of(1266, 1, 61), LocalDate.of(  100,  3,  2) },
            // Last two days of a Discordian year map onto the last two days of the ISO year
            { DiscordianDate.of(1166, 5, 73), LocalDate.of(    0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72), LocalDate.of(    0, 12, 30) },
            // Historic dates around the Gregorian reform (Oct 1582)
            { DiscordianDate.of(2748, 4, 68), LocalDate.of( 1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69), LocalDate.of( 1582, 10, 15) },
            // Modern historical dates
            { DiscordianDate.of(3111, 5, 24), LocalDate.of( 1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40), LocalDate.of( 2012,  7,  5) },
            { DiscordianDate.of(3178, 3, 41), LocalDate.of( 2012,  7,  6) },
        };
    }

    /**
     * Verifies that {@code DiscordianDate.until(endDate, DAYS)} returns the
     * correct signed day count for offsets of 0, +1, +35, and -40 days relative
     * to each known Discordian-to-ISO date correspondence in {@code data_samples}.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(DiscordianDate discordian, LocalDate iso) {
        assertEquals(  0, discordian.until(iso.plusDays( 0), DAYS));
        assertEquals(  1, discordian.until(iso.plusDays( 1), DAYS));
        assertEquals( 35, discordian.until(iso.plusDays(35), DAYS));
        assertEquals(-40, discordian.until(iso.minusDays(40), DAYS));
    }
}
