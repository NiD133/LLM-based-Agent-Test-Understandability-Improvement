package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Tests that applying lastDayOfMonth() to St. Tib's Day leaves it unchanged,
 * because St. Tib's Day is its own single-day "month" (month 0, day 0).
 */
public class TestDiscordianChronology_test_adjust1 {

    // St. Tib's Day: intercalary leap day represented as month=0, day=0
    private static final DiscordianDate ST_TIBS_DAY = DiscordianDate.of(2014, 0, 0);

    @Test
    public void test_adjust1() {
        // lastDayOfMonth on St. Tib's Day should return the same date —
        // it is both the first and last day of its own month
        DiscordianDate result = ST_TIBS_DAY.with(TemporalAdjusters.lastDayOfMonth());
        assertEquals(ST_TIBS_DAY, result);
    }
}
