package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a {@link DiscordianDate} with an ISO {@link Month} is rejected.
 *
 * <p>A Discordian year is divided into five 73-day seasons rather than the twelve ISO months,
 * so an ISO {@code Month} is not a valid adjuster for a Discordian date and must be refused.
 */
public class TestDiscordianChronology_test_adjust_toMonth {

    @Test
    public void test_adjust_toMonth() {
        DiscordianDate discordianDate = DiscordianDate.of(2000, 1, 4);

        // Adjusting with an ISO Month is unsupported by the Discordian calendar.
        assertThrows(DateTimeException.class, () -> discordianDate.with(Month.APRIL));
    }
}
