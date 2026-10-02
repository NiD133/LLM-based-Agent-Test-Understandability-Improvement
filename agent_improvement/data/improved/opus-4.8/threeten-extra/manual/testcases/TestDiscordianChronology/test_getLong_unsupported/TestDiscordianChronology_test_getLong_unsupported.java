package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.MINUTE_OF_DAY;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianDate#getLong} rejects a temporal field that the
 * Discordian calendar does not support.
 */
public class TestDiscordianChronology_test_getLong_unsupported {

    @Test
    public void getLong_withTimeBasedField_throwsUnsupportedTemporalType() {
        // MINUTE_OF_DAY is a time-of-day field, so a date-only DiscordianDate
        // cannot resolve it and must reject the request.
        DiscordianDate date = DiscordianDate.of(2012, 1, 30);

        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> date.getLong(MINUTE_OF_DAY));
    }
}
