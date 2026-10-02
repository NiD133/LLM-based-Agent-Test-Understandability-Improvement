package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_LocalDate_adjustToDiscordianDate {

    /**
     * Verifies that a DiscordianDate can be used as a TemporalAdjuster to convert
     * any ISO LocalDate to the ISO equivalent of that Discordian date.
     *
     * Discordian year 3178, season 3, day 41 corresponds to ISO 2012-07-06.
     * Calling LocalDate.MIN.with(discordianDate) uses DiscordianDate.adjustInto()
     * to project the Discordian date back onto the ISO timeline.
     */
    @Test
    public void test_LocalDate_adjustToDiscordianDate() {
        // Discordian YOLD 3178, Confusion (month 3), day 41  →  ISO 2012-07-06
        DiscordianDate discordianDate = DiscordianDate.of(3178, 3, 41);

        LocalDate result = LocalDate.MIN.with(discordianDate);

        assertEquals(LocalDate.of(2012, 7, 6), result);
    }
}
