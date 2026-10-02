package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_adjust2 {

    @Test
    public void test_adjust2() {
        DiscordianDate base = DiscordianDate.of(2012, 2, 23);

        DiscordianDate lastDayOfSameMonth = base.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(DiscordianDate.of(2012, 2, 73), lastDayOfSameMonth);
    }
}
