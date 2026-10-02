package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_adjust2 {

    /**
     * Verifies that applying lastDayOfMonth() to a Discordian date moves it to
     * day 73, which is the last day of any standard Discordian month.
     */
    @Test
    public void test_adjust2() {
        DiscordianDate base = DiscordianDate.of(2012, 2, 23);
        DiscordianDate test = base.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(DiscordianDate.of(2012, 2, 73), test);
    }
}
