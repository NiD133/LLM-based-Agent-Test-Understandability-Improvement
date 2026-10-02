package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link MutableClock#of(java.time.Instant, java.time.ZoneId)}.
 * <p>
 * The factory must build a clock that faithfully reports back the instant and
 * the time-zone it was created with.
 */
public class TestMutableClock_test_of {

    @Test
    public void of_clockReportsTheInstantItWasCreatedWith() {
        // The clock's instant() must echo the instant passed to of(), including
        // the boundary values of the Instant range.
        assertEquals(Instant.EPOCH, MutableClock.of(Instant.EPOCH, ZoneOffset.UTC).instant());
        assertEquals(Instant.MIN, MutableClock.of(Instant.MIN, ZoneOffset.UTC).instant());
        assertEquals(Instant.MAX, MutableClock.of(Instant.MAX, ZoneOffset.UTC).instant());
    }

    @Test
    public void of_clockReportsTheZoneItWasCreatedWith() {
        // The clock's getZone() must echo the zone passed to of(), including the
        // boundary values of the ZoneOffset range.
        assertEquals(ZoneOffset.UTC, MutableClock.of(Instant.EPOCH, ZoneOffset.UTC).getZone());
        assertEquals(ZoneOffset.MIN, MutableClock.of(Instant.EPOCH, ZoneOffset.MIN).getZone());
        assertEquals(ZoneOffset.MAX, MutableClock.of(Instant.EPOCH, ZoneOffset.MAX).getZone());
    }
}
