package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link DiscordianDate#until(java.time.temporal.Temporal)} when the target is the
 * ISO {@link LocalDate} that represents exactly the same day.
 *
 * <p>Because both dates refer to the same instant on the timeline, the elapsed
 * period between them must be zero (a Discordian period of 0 years, 0 months, 0 days).
 */
public class TestDiscordianChronology_test_DiscordianDate_until_LocalDate {

    /**
     * Each row pairs a Discordian date with the ISO {@link LocalDate} for the very same day.
     * The samples deliberately cover boundary situations such as:
     * <ul>
     *   <li>proleptic and pre-year-1 years,</li>
     *   <li>the days around the Gregorian end of February (leap and non-leap),</li>
     *   <li>St. Tib's Day, represented as month 0 / day 0 (e.g. year 1170), and</li>
     *   <li>historical reference dates (1582 calendar switch, 1945, 2012).</li>
     * </ul>
     */
    public static Object[][] equivalentDiscordianAndIsoDates() {
        return new Object[][] {
            { DiscordianDate.of(2, 1, 1), LocalDate.of(-1164, 1, 1) },
            { DiscordianDate.of(166, 1, 1), LocalDate.of(-1000, 1, 1) },
            { DiscordianDate.of(1156, 1, 1), LocalDate.of(-10, 1, 1) },
            { DiscordianDate.of(1166, 1, 1), LocalDate.of(0, 1, 1) },
            { DiscordianDate.of(1167, 1, 1), LocalDate.of(1, 1, 1) },
            { DiscordianDate.of(1167, 1, 2), LocalDate.of(1, 1, 2) },
            { DiscordianDate.of(1167, 1, 3), LocalDate.of(1, 1, 3) },
            // Days spanning the end of February in a non-leap year (year 1).
            { DiscordianDate.of(1167, 1, 57), LocalDate.of(1, 2, 26) },
            { DiscordianDate.of(1167, 1, 58), LocalDate.of(1, 2, 27) },
            { DiscordianDate.of(1167, 1, 59), LocalDate.of(1, 2, 28) },
            { DiscordianDate.of(1167, 1, 60), LocalDate.of(1, 3, 1) },
            // Leap year 4: St. Tib's Day (month 0 / day 0) sits between Feb 28 and Mar 1.
            { DiscordianDate.of(1170, 1, 57), LocalDate.of(4, 2, 26) },
            { DiscordianDate.of(1170, 1, 58), LocalDate.of(4, 2, 27) },
            { DiscordianDate.of(1170, 1, 59), LocalDate.of(4, 2, 28) },
            { DiscordianDate.of(1170, 0, 0), LocalDate.of(4, 2, 29) },
            { DiscordianDate.of(1170, 1, 60), LocalDate.of(4, 3, 1) },
            // Non-leap century year 100.
            { DiscordianDate.of(1266, 1, 57), LocalDate.of(100, 2, 26) },
            { DiscordianDate.of(1266, 1, 58), LocalDate.of(100, 2, 27) },
            { DiscordianDate.of(1266, 1, 59), LocalDate.of(100, 2, 28) },
            { DiscordianDate.of(1266, 1, 60), LocalDate.of(100, 3, 1) },
            { DiscordianDate.of(1266, 1, 61), LocalDate.of(100, 3, 2) },
            // End of year 0.
            { DiscordianDate.of(1166, 5, 73), LocalDate.of(0, 12, 31) },
            { DiscordianDate.of(1166, 5, 72), LocalDate.of(0, 12, 30) },
            // Historical reference dates.
            { DiscordianDate.of(2748, 4, 68), LocalDate.of(1582, 10, 14) },
            { DiscordianDate.of(2748, 4, 69), LocalDate.of(1582, 10, 15) },
            { DiscordianDate.of(3111, 5, 24), LocalDate.of(1945, 11, 12) },
            { DiscordianDate.of(3178, 3, 40), LocalDate.of(2012, 7, 5) },
            { DiscordianDate.of(3178, 3, 41), LocalDate.of(2012, 7, 6) },
        };
    }

    @ParameterizedTest
    @MethodSource("equivalentDiscordianAndIsoDates")
    public void until_sameDayAsIsoDate_returnsZeroPeriod(DiscordianDate discordianDate, LocalDate sameDayAsIso) {
        assertEquals(DiscordianChronology.INSTANCE.period(0, 0, 0), discordianDate.until(sameDayAsIso));
    }
}
