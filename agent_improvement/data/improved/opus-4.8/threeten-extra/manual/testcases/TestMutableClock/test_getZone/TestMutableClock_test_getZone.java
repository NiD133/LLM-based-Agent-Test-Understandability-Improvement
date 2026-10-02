package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link MutableClock#getZone()} reports the time-zone the clock was
 * created with, and that deriving a new clock via {@link MutableClock#withZone}
 * or {@link MutableClock#of} establishes the expected time-zone.
 */
public class TestMutableClock_test_getZone {

    @Test
    public void getZone_returnsTheZoneEachClockWasCreatedWith() {
        // A clock from epochUTC() uses the UTC time-zone.
        MutableClock utcClock = MutableClock.epochUTC();
        assertEquals(ZoneOffset.UTC, utcClock.getZone());

        // withZone() produces a view that reports the requested time-zone.
        MutableClock minZoneClock = utcClock.withZone(ZoneOffset.MIN);
        assertEquals(ZoneOffset.MIN, minZoneClock.getZone());

        // of() produces a clock that reports the time-zone passed to it.
        MutableClock maxZoneClock = MutableClock.of(Instant.EPOCH, ZoneOffset.MAX);
        assertEquals(ZoneOffset.MAX, maxZoneClock.getZone());
    }
}
