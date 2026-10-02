package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_adjust_toMonth {

    /**
     * Discordian dates cannot be adjusted to an ISO Month because Month is an ISO concept
     * incompatible with the Discordian calendar system. Attempting to do so must throw
     * DateTimeException.
     */
    @Test
    public void test_adjust_toMonth() {
        DiscordianDate date = DiscordianDate.of(2000, 1, 4);
        assertThrows(DateTimeException.class, () -> date.with(Month.APRIL));
    }
}
