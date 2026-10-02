package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianDate#range(java.time.temporal.TemporalField)} rejects
 * temporal fields it does not support.
 */
public class TestDiscordianChronology_test_range_unsupported {

    @Test
    public void range_withTimeBasedField_throwsUnsupported() {
        DiscordianDate date = DiscordianDate.of(2012, 5, 30);

        // MINUTE_OF_DAY is a time field, so a date-only type cannot provide a range for it.
        assertThrows(UnsupportedTemporalTypeException.class, () -> date.range(MINUTE_OF_DAY));
    }
}
