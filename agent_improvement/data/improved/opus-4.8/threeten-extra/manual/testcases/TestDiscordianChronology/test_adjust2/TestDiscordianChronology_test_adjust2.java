package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a {@link DiscordianDate} with
 * {@link TemporalAdjusters#lastDayOfMonth()} moves the date to the final day
 * of its Discordian month (day 73).
 */
public class TestDiscordianChronology_test_adjust2 {

    @Test
    public void with_lastDayOfMonth_returnsDay73OfSameMonth() {
        // A Discordian month always has 73 days, so the last day of month 2 is day 73.
        DiscordianDate dateInMonth2 = DiscordianDate.of(2012, 2, 23);

        DiscordianDate lastDayOfMonth = dateInMonth2.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(DiscordianDate.of(2012, 2, 73), lastDayOfMonth);
    }
}
