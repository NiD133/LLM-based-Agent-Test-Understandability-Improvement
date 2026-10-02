package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_LocalDateTime_adjustToDiscordianDate {

    /**
     * Adjusting an ISO {@link LocalDateTime} with a {@link DiscordianDate} should
     * move it to the equivalent ISO date while leaving the time-of-day untouched.
     * Discordian YOLD 3178-3-41 corresponds to the ISO date 2012-07-06.
     */
    @Test
    public void test_LocalDateTime_adjustToDiscordianDate() {
        DiscordianDate discordianDate = DiscordianDate.of(3178, 3, 41);

        LocalDateTime adjusted = LocalDateTime.MIN.with(discordianDate);

        LocalDateTime expected = LocalDateTime.of(2012, 7, 6, 0, 0);
        assertEquals(expected, adjusted);
    }
}
