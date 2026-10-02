package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link DiscordianDate#from(java.time.temporal.TemporalAccessor)}
 * converts an ISO {@link LocalDate} into the equivalent Discordian date.
 * <p>
 * Each case pairs an ISO date with the Discordian date it should map to. The
 * cases deliberately cover the calendar's edge behaviour:
 * <ul>
 *   <li>the calendar's start near YOLD 1 (ISO BCE 1166);</li>
 *   <li>the boundary around St. Tib's Day, the leap day that sits between the
 *       59th and 60th day of the first month (e.g. ISO Feb 29 of a leap year,
 *       represented as Discordian month 0, day 0);</li>
 *   <li>ordinary days within and across month boundaries.</li>
 * </ul>
 */
public class TestDiscordianChronology_test_DiscordianDate_from_LocalDate {

    @Test
    public void from_localDate_returnsEquivalentDiscordianDate() {
        // Each pair: { expected Discordian date, ISO date to convert }.
        assertConversion(DiscordianDate.of(2, 1, 1), LocalDate.of(-1164, 1, 1));
        assertConversion(DiscordianDate.of(166, 1, 1), LocalDate.of(-1000, 1, 1));
        assertConversion(DiscordianDate.of(1156, 1, 1), LocalDate.of(-10, 1, 1));
        assertConversion(DiscordianDate.of(1166, 1, 1), LocalDate.of(0, 1, 1));

        // Discordian year 1167 aligns with ISO year 1; walk the first month.
        assertConversion(DiscordianDate.of(1167, 1, 1), LocalDate.of(1, 1, 1));
        assertConversion(DiscordianDate.of(1167, 1, 2), LocalDate.of(1, 1, 2));
        assertConversion(DiscordianDate.of(1167, 1, 3), LocalDate.of(1, 1, 3));
        assertConversion(DiscordianDate.of(1167, 1, 57), LocalDate.of(1, 2, 26));
        assertConversion(DiscordianDate.of(1167, 1, 58), LocalDate.of(1, 2, 27));
        assertConversion(DiscordianDate.of(1167, 1, 59), LocalDate.of(1, 2, 28));
        assertConversion(DiscordianDate.of(1167, 1, 60), LocalDate.of(1, 3, 1));

        // Leap year (ISO year 4): St. Tib's Day appears as month 0, day 0
        // between days 59 and 60, matching ISO Feb 29.
        assertConversion(DiscordianDate.of(1170, 1, 57), LocalDate.of(4, 2, 26));
        assertConversion(DiscordianDate.of(1170, 1, 58), LocalDate.of(4, 2, 27));
        assertConversion(DiscordianDate.of(1170, 1, 59), LocalDate.of(4, 2, 28));
        assertConversion(DiscordianDate.of(1170, 0, 0), LocalDate.of(4, 2, 29));
        assertConversion(DiscordianDate.of(1170, 1, 60), LocalDate.of(4, 3, 1));

        // ISO year 100 is not a leap year, so the days shift straight through.
        assertConversion(DiscordianDate.of(1266, 1, 57), LocalDate.of(100, 2, 26));
        assertConversion(DiscordianDate.of(1266, 1, 58), LocalDate.of(100, 2, 27));
        assertConversion(DiscordianDate.of(1266, 1, 59), LocalDate.of(100, 2, 28));
        assertConversion(DiscordianDate.of(1266, 1, 60), LocalDate.of(100, 3, 1));
        assertConversion(DiscordianDate.of(1266, 1, 61), LocalDate.of(100, 3, 2));

        // End of an ISO year maps to the end of the last Discordian month.
        assertConversion(DiscordianDate.of(1166, 5, 73), LocalDate.of(0, 12, 31));
        assertConversion(DiscordianDate.of(1166, 5, 72), LocalDate.of(0, 12, 30));

        // Assorted historical and modern dates.
        assertConversion(DiscordianDate.of(2748, 4, 68), LocalDate.of(1582, 10, 14));
        assertConversion(DiscordianDate.of(2748, 4, 69), LocalDate.of(1582, 10, 15));
        assertConversion(DiscordianDate.of(3111, 5, 24), LocalDate.of(1945, 11, 12));
        assertConversion(DiscordianDate.of(3178, 3, 40), LocalDate.of(2012, 7, 5));
        assertConversion(DiscordianDate.of(3178, 3, 41), LocalDate.of(2012, 7, 6));
    }

    private static void assertConversion(DiscordianDate expectedDiscordian, LocalDate isoDate) {
        assertEquals(expectedDiscordian, DiscordianDate.from(isoDate));
    }
}
