package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting a Discordian {@link ChronoPeriod} from a
 * {@link DiscordianDate} shifts the date by the expected amount.
 */
public class TestDiscordianChronology_test_minus_Period {

    @Test
    public void minusPeriod_subtractsMonthsAndDays() {
        DiscordianDate startDate = DiscordianDate.of(2014, 5, 26);
        ChronoPeriod twoMonthsThreeDays = DiscordianChronology.INSTANCE.period(0, 2, 3);

        DiscordianDate result = startDate.minus(twoMonthsThreeDays);

        assertEquals(DiscordianDate.of(2014, 3, 23), result);
    }
}
