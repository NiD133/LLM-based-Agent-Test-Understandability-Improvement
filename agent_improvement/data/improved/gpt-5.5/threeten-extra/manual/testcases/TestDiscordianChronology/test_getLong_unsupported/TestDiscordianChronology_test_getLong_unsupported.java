package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_getLong_unsupported {

    @Test
    public void test_getLong_unsupported() {
        DiscordianDate date = DiscordianDate.of(2012, 1, 30);

        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> date.getLong(MINUTE_OF_DAY));
    }
}
