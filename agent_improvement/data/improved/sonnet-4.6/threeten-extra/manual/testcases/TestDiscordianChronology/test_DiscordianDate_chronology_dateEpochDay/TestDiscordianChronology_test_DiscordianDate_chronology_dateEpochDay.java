package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link DiscordianChronology#dateEpochDay(long)} correctly converts
 * an ISO epoch-day value to the equivalent {@link DiscordianDate}.
 *
 * <p>Each sample row contains a pre-constructed {@code DiscordianDate} and the
 * corresponding ISO {@code LocalDate}.  The test converts the ISO date to its
 * epoch-day and asserts that the chronology rebuilds the same Discordian date.
 */
public class TestDiscordianChronology_test_DiscordianDate_chronology_dateEpochDay {

    // ---------------------------------------------------------------------------
    // Test data: pairs of (expected DiscordianDate, equivalent ISO LocalDate).
    // Discordian year = ISO year + 1166.  Month 0 / day 0 represents St. Tib's Day.
    // ---------------------------------------------------------------------------
    public static Object[][] data_samples() {
        return new Object[][] {
            // Ancient / negative ISO years
            { DiscordianDate.of(   2, 1,  1), LocalDate.of(-1164,  1,  1) },
            { DiscordianDate.of( 166, 1,  1), LocalDate.of(-1000,  1,  1) },
            { DiscordianDate.of(1156, 1,  1), LocalDate.of(  -10,  1,  1) },

            // ISO year 0 boundary
            { DiscordianDate.of(1166, 1,  1), LocalDate.of(    0,  1,  1) },
            { DiscordianDate.of(1166, 5, 73), LocalDate.of(    0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72), LocalDate.of(    0, 12, 30) },

            // ISO year 1 – start and early days
            { DiscordianDate.of(1167, 1,  1), LocalDate.of(    1,  1,  1) },
            { DiscordianDate.of(1167, 1,  2), LocalDate.of(    1,  1,  2) },
            { DiscordianDate.of(1167, 1,  3), LocalDate.of(    1,  1,  3) },

            // ISO year 1 – days around February (non-leap): no St. Tib's Day
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(    1,  2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(    1,  2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(    1,  2, 28) },
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(    1,  3,  1) },

            // ISO year 4 (leap year) – February/March boundary with St. Tib's Day
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(    4,  2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(    4,  2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(    4,  2, 28) },
            { DiscordianDate.of(1170, 0,  0), LocalDate.of(    4,  2, 29) }, // St. Tib's Day
            { DiscordianDate.of(1170, 1, 60), LocalDate.of(    4,  3,  1) },

            // ISO year 100 (century, non-leap) – February/March boundary
            { DiscordianDate.of(1266, 1, 57), LocalDate.of(  100,  2, 26) },
            { DiscordianDate.of(1266, 1, 58), LocalDate.of(  100,  2, 27) },
            { DiscordianDate.of(1266, 1, 59), LocalDate.of(  100,  2, 28) },
            { DiscordianDate.of(1266, 1, 60), LocalDate.of(  100,  3,  1) },
            { DiscordianDate.of(1266, 1, 61), LocalDate.of(  100,  3,  2) },

            // Gregorian calendar reform boundary (Oct 1582)
            { DiscordianDate.of(2748, 4, 68), LocalDate.of( 1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69), LocalDate.of( 1582, 10, 15) },

            // Historical date
            { DiscordianDate.of(3111, 5, 24), LocalDate.of( 1945, 11, 12) },

            // Modern dates
            { DiscordianDate.of(3178, 3, 40), LocalDate.of( 2012,  7,  5) },
            { DiscordianDate.of(3178, 3, 41), LocalDate.of( 2012,  7,  6) },
        };
    }

    // ---------------------------------------------------------------------------
    // Test
    // ---------------------------------------------------------------------------

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_DiscordianDate_chronology_dateEpochDay(DiscordianDate discordian, LocalDate iso) {
        assertEquals(discordian, DiscordianChronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }
}
