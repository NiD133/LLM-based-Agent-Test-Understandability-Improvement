package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianDate#with(java.time.temporal.TemporalField, long)}
 * rejects a time-based field, which a date has no way to represent.
 */
public class TestDiscordianChronology_test_with_TemporalField_unsupported {

    @Test
    public void with_timeBasedField_throwsUnsupportedTemporalType() {
        DiscordianDate date = DiscordianDate.of(2012, 5, 30);

        // MINUTE_OF_DAY is a time field, so it is not supported on a date-only value.
        assertThrows(UnsupportedTemporalTypeException.class, () -> date.with(MINUTE_OF_DAY, 0));
    }
}
