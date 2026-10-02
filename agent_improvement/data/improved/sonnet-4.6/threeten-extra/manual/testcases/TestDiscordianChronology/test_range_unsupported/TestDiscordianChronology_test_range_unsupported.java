package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that DiscordianDate.range() throws UnsupportedTemporalTypeException
 * for temporal fields that are not supported by the Discordian calendar.
 */
public class TestDiscordianChronology_test_range_unsupported {

    @Test
    public void test_range_unsupported() {
        // MINUTE_OF_DAY is a time-based field that has no meaning for a date-only type
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> DiscordianDate.of(2012, 5, 30).range(MINUTE_OF_DAY));
    }
}
