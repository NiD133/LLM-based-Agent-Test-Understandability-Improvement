package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

/**
 * Verifies how a Discordian date representing St. Tib's Day reacts to the
 * {@code lastDayOfMonth} temporal adjuster.
 */
public class TestDiscordianChronology_test_adjust1 {

    /**
     * St. Tib's Day (encoded as month 0, day 0) sits in its own one-day month,
     * so adjusting it to the "last day of month" must leave it unchanged.
     */
    @Test
    public void adjustingStTibsDayToLastDayOfMonthReturnsSameDate() {
        DiscordianDate stTibsDay = DiscordianDate.of(2014, 0, 0);

        DiscordianDate adjusted = stTibsDay.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(DiscordianDate.of(2014, 0, 0), adjusted);
    }
}
