package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_with_TemporalField_unsupported {

    // MINUTE_OF_DAY is a time-based field that has no meaning in a date-only calendar,
    // so DiscordianDate.with() must reject it with UnsupportedTemporalTypeException.
    @Test
    public void test_with_TemporalField_unsupported() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> DiscordianDate.of(2012, 5, 30).with(MINUTE_OF_DAY, 0));
    }
}
