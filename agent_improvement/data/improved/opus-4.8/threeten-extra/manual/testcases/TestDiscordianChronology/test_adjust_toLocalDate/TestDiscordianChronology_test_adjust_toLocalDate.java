package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a {@link DiscordianDate} with an ISO {@link LocalDate}
 * yields the Discordian date corresponding to that ISO date.
 */
public class TestDiscordianChronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        // Starting Discordian date is irrelevant: adjusting with a LocalDate
        // replaces it entirely with the equivalent Discordian date.
        DiscordianDate startingDate = DiscordianDate.of(2000, 1, 4);
        LocalDate isoDate = LocalDate.of(2012, 7, 6);

        DiscordianDate adjusted = startingDate.with(isoDate);

        // ISO 2012-07-06 corresponds to Discordian year 3178, month 3, day 41.
        assertEquals(DiscordianDate.of(3178, 3, 41), adjusted);
    }
}
