package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_LocalDateTime_adjustToDiscordianDate {

    /**
     * Verifies that adjusting a LocalDateTime to a DiscordianDate produces the
     * corresponding ISO date-time. DiscordianDate YOLD 3178-3-41 maps to ISO 2012-07-06;
     * the time portion of LocalDateTime.MIN (00:00) is preserved.
     */
    @Test
    public void test_LocalDateTime_adjustToDiscordianDate() {
        DiscordianDate discordianDate = DiscordianDate.of(3178, 3, 41);
        LocalDateTime adjusted = LocalDateTime.MIN.with(discordianDate);
        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), adjusted);
    }
}
