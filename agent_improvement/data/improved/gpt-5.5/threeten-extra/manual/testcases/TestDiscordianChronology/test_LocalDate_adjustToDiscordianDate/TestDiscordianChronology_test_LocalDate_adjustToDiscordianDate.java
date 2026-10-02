package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_LocalDate_adjustToDiscordianDate {

    @Test
    public void test_LocalDate_adjustToDiscordianDate() {
        DiscordianDate discordianDate = DiscordianDate.of(3178, 3, 41);

        LocalDate adjustedDate = LocalDate.MIN.with(discordianDate);

        assertEquals(LocalDate.of(2012, 7, 6), adjustedDate);
    }
}
