package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianDate#plus(long, java.time.temporal.TemporalUnit)}
 * rejects time-based units, which the date-only Discordian calendar cannot support.
 */
public class TestDiscordianChronology_test_plus_TemporalUnit_unsupported {

    @Test
    public void plus_withTimeBasedUnit_throwsUnsupportedTemporalTypeException() {
        DiscordianDate date = DiscordianDate.of(2012, 5, 30);

        // MINUTES is a time-based unit and is not valid for a calendar date.
        assertThrows(UnsupportedTemporalTypeException.class, () -> date.plus(0, MINUTES));
    }
}
