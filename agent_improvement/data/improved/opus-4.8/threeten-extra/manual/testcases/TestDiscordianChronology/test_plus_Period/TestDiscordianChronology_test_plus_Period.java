package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests adding a Discordian {@link java.time.chrono.ChronoPeriod} to a {@link DiscordianDate}.
 */
public class TestDiscordianChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        // Start date: year 2014, month 5, day 26.
        DiscordianDate startDate = DiscordianDate.of(2014, 5, 26);

        // Period to add: 0 years, 2 months, 3 days.
        // Adding 2 months to month 5 rolls into the next year (Discordian years have 5 months),
        // and adding 3 days moves day 26 to day 29.
        java.time.chrono.ChronoPeriod twoMonthsThreeDays =
                DiscordianChronology.INSTANCE.period(0, 2, 3);

        // Expected result: year 2015, month 2, day 29.
        DiscordianDate expectedDate = DiscordianDate.of(2015, 2, 29);

        assertEquals(expectedDate, startDate.plus(twoMonthsThreeDays));
    }
}
