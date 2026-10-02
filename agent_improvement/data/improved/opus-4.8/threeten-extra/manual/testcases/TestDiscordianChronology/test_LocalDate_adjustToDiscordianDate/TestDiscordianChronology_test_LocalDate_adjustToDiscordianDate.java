package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * Verifies that adjusting a {@link LocalDate} with a {@link DiscordianDate} via
 * {@link LocalDate#with(java.time.temporal.TemporalAdjuster)} yields the ISO date
 * that corresponds to the given Discordian date.
 */
public class TestDiscordianChronology_test_LocalDate_adjustToDiscordianDate {

    @Test
    public void test_LocalDate_adjustToDiscordianDate() {
        // Discordian year 3178, season 3, day 41 corresponds to ISO 2012-07-06.
        DiscordianDate discordianDate = DiscordianDate.of(3178, 3, 41);
        LocalDate expectedIsoDate = LocalDate.of(2012, 7, 6);

        // Adjusting any LocalDate (here LocalDate.MIN) with the Discordian date
        // must produce the equivalent ISO date.
        LocalDate adjustedIsoDate = LocalDate.MIN.with(discordianDate);

        assertEquals(expectedIsoDate, adjustedIsoDate);
    }
}
