package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that subtracting a Discordian {@link java.time.chrono.ChronoPeriod} from a
 * {@link DiscordianDate} via {@link DiscordianDate#minus} walks the date back by the
 * expected number of months and days.
 */
public class TestDiscordianChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Start from season 5, day 26 of year 2014.
        DiscordianDate startDate = DiscordianDate.of(2014, 5, 26);

        // Subtract a period of 0 years, 2 months (seasons) and 3 days.
        DiscordianDate result = startDate.minus(DiscordianChronology.INSTANCE.period(0, 2, 3));

        // Expect to land on season 3, day 23 of the same year.
        DiscordianDate expectedDate = DiscordianDate.of(2014, 3, 23);
        assertEquals(expectedDate, result);
    }
}
