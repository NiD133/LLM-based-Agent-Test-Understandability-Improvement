package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_adjust1 {

    @Test
    public void test_adjust1() {
        DiscordianDate stTibsDay = DiscordianDate.of(2014, 0, 0);

        DiscordianDate adjustedDate = stTibsDay.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(DiscordianDate.of(2014, 0, 0), adjustedDate);
    }
}
