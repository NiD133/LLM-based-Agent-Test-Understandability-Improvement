package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link DiscordianDate#minus(java.time.temporal.TemporalAmount)} with a
 * Discordian {@link ChronoPeriod}.
 */
public class TestDiscordianChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Subtracting 2 months and 3 days from the 26th day of month 5
        // moves the date back to the 23rd day of month 3.
        DiscordianDate startDate = DiscordianDate.of(2014, 5, 26);
        ChronoPeriod twoMonthsThreeDays = DiscordianChronology.INSTANCE.period(0, 2, 3);
        DiscordianDate expectedDate = DiscordianDate.of(2014, 3, 23);

        assertEquals(expectedDate, startDate.minus(twoMonthsThreeDays));
    }
}
