package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_minus_Period {

    /**
     * Subtracting a period of 0 years, 2 months, and 3 days from YOLD 2014-5-26
     * should yield YOLD 2014-3-23 (two months back lands in month 3, minus 3 days from day 26).
     */
    @Test
    public void test_minus_Period() {
        DiscordianDate startDate = DiscordianDate.of(2014, 5, 26);
        ChronoPeriod period = DiscordianChronology.INSTANCE.period(0, 2, 3);
        DiscordianDate expectedDate = DiscordianDate.of(2014, 3, 23);

        DiscordianDate result = startDate.minus(period);

        assertEquals(expectedDate, result);
    }
}
