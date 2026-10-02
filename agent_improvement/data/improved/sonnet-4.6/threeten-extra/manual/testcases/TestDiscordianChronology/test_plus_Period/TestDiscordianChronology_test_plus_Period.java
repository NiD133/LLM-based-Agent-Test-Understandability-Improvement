package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        // Starting date: Chaos 26, YOLD 3180 (Discordian year 2014, month 5, day 26)
        DiscordianDate startDate = DiscordianDate.of(2014, 5, 26);

        // Period to add: 0 years, 2 months, 3 days
        ChronoPeriod twoMonthsThreeDays = DiscordianChronology.INSTANCE.period(0, 2, 3);

        // Expected: advancing 2 months wraps from month 5 into next year's month 2,
        // then adding 3 days gives day 29 → YOLD 3181, month 2, day 29
        DiscordianDate expectedDate = DiscordianDate.of(2015, 2, 29);

        assertEquals(expectedDate, startDate.plus(twoMonthsThreeDays));
    }
}
