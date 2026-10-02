package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;
import static java.time.temporal.ChronoField.YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#set(java.time.temporal.TemporalField, long)}
 * updates individual date-time fields of the clock one at a time, and that the
 * combined result is reflected by {@link MutableClock#instant()}.
 */
public class TestMutableClock_test_set_fieldAndValue {

    @Test
    public void test_set_fieldAndValue() {
        // Start from the epoch (1970-01-01T00:00:00Z) in UTC.
        MutableClock clock = MutableClock.epochUTC();

        // Set each calendar/time field individually to build up 0000-01-02T03:04:05.
        clock.set(YEAR, 0);
        clock.set(MONTH_OF_YEAR, 1);
        clock.set(DAY_OF_MONTH, 2);
        clock.set(HOUR_OF_DAY, 3);
        clock.set(MINUTE_OF_HOUR, 4);
        clock.set(SECOND_OF_MINUTE, 5);

        // The clock's instant should equal the fully-assembled date-time in UTC.
        Instant expected = LocalDateTime.of(0, 1, 2, 3, 4, 5)
                .atZone(ZoneOffset.UTC)
                .toInstant();
        assertEquals(expected, clock.instant());
    }
}
