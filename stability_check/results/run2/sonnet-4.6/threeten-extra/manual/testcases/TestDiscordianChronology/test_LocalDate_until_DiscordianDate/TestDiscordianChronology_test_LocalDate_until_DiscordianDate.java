package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@code LocalDate.until(DiscordianDate)} returns {@link Period#ZERO}
 * when the ISO LocalDate and the DiscordianDate represent the same calendar day.
 *
 * <p>Each row in {@link #data_samples()} is a (discordianDate, isoDate) pair that
 * denotes the same instant in time. The method under test converts the Discordian
 * date back to ISO and then computes the period between the two identical days, so
 * the result must always be zero.
 */
public class TestDiscordianChronology_test_LocalDate_until_DiscordianDate {

    /**
     * Pairs of (DiscordianDate, LocalDate) that represent the same day, covering:
     * <ul>
     *   <li>Ancient proleptic years</li>
     *   <li>Year-boundary / epoch transitions</li>
     *   <li>The boundary between Discordian month 1 and month 2 (around ISO Feb 26-Mar 1)</li>
     *   <li>Leap-year St. Tib's Day (month 0, day 0)</li>
     *   <li>A century year that is NOT a leap year (ISO year 100)</li>
     *   <li>Historic calendar-reform boundary (ISO Oct 1582)</li>
     *   <li>A few modern dates</li>
     * </ul>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Ancient / BCE dates
            { DiscordianDate.of(2,    1,  1), LocalDate.of(-1164,  1,  1) },
            { DiscordianDate.of(166,  1,  1), LocalDate.of(-1000,  1,  1) },
            { DiscordianDate.of(1156, 1,  1), LocalDate.of(  -10,  1,  1) },

            // ISO year 0 / year 1 boundary
            { DiscordianDate.of(1166, 1,  1), LocalDate.of(    0,  1,  1) },
            { DiscordianDate.of(1167, 1,  1), LocalDate.of(    1,  1,  1) },
            { DiscordianDate.of(1167, 1,  2), LocalDate.of(    1,  1,  2) },
            { DiscordianDate.of(1167, 1,  3), LocalDate.of(    1,  1,  3) },

            // End of Discordian month 1 / start of month 2 in a non-leap year (ISO year 1)
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(    1,  2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(    1,  2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(    1,  2, 28) },
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(    1,  3,  1) },

            // Same boundary in a leap year (ISO year 4): St. Tib's Day sits between day 59 and day 60
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(    4,  2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(    4,  2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(    4,  2, 28) },
            { DiscordianDate.of(1170, 0,  0), LocalDate.of(    4,  2, 29) },  // St. Tib's Day (leap day)
            { DiscordianDate.of(1170, 1, 60), LocalDate.of(    4,  3,  1) },

            // Century year that is NOT a leap year (ISO year 100)
            { DiscordianDate.of(1266, 1, 57), LocalDate.of(  100,  2, 26) },
            { DiscordianDate.of(1266, 1, 58), LocalDate.of(  100,  2, 27) },
            { DiscordianDate.of(1266, 1, 59), LocalDate.of(  100,  2, 28) },
            { DiscordianDate.of(1266, 1, 60), LocalDate.of(  100,  3,  1) },
            { DiscordianDate.of(1266, 1, 61), LocalDate.of(  100,  3,  2) },

            // End of ISO year 0
            { DiscordianDate.of(1166, 5, 73), LocalDate.of(    0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72), LocalDate.of(    0, 12, 30) },

            // Gregorian calendar reform boundary (Oct 1582)
            { DiscordianDate.of(2748, 4, 68), LocalDate.of( 1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69), LocalDate.of( 1582, 10, 15) },

            // A historical modern date
            { DiscordianDate.of(3111, 5, 24), LocalDate.of( 1945, 11, 12) },

            // Two consecutive modern dates
            { DiscordianDate.of(3178, 3, 40), LocalDate.of( 2012,  7,  5) },
            { DiscordianDate.of(3178, 3, 41), LocalDate.of( 2012,  7,  6) },
        };
    }

    /**
     * Verifies that the period from an ISO {@link LocalDate} to the equivalent
     * {@link DiscordianDate} is always {@link Period#ZERO}, because both objects
     * represent the same day.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_until_DiscordianDate(DiscordianDate discordian, LocalDate iso) {
        assertEquals(Period.ZERO, iso.until(discordian));
    }
}
