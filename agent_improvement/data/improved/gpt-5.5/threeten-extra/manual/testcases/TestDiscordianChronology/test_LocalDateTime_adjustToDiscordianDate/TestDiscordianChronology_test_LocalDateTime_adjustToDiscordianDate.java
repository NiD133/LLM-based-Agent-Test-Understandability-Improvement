package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_LocalDateTime_adjustToDiscordianDate {

    @Test
    public void test_LocalDateTime_adjustToDiscordianDate() {
        DiscordianDate discordian = DiscordianDate.of(3178, 3, 41);

        LocalDateTime adjustedDateTime = LocalDateTime.MIN.with(discordian);

        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), adjustedDateTime);
    }
}
