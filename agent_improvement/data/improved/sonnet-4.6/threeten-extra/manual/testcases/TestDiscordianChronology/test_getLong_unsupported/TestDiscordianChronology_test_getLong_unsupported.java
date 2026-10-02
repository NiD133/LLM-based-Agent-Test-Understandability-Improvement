package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_getLong_unsupported {

    // MINUTE_OF_DAY is a time-of-day field not applicable to date-only Discordian dates
    @Test
    public void test_getLong_unsupported() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> DiscordianDate.of(2012, 1, 30).getLong(MINUTE_OF_DAY));
    }
}
