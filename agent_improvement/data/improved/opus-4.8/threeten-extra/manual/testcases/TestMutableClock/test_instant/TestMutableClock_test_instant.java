package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#instant()} reflects the clock's current
 * instant, including after the clock is advanced or reset, and that views
 * sharing the same underlying clock observe the same instant.
 */
public class TestMutableClock_test_instant {

    @Test
    public void test_instant() {
        // A fresh epoch clock starts at the Unix epoch instant.
        MutableClock clock = MutableClock.epochUTC();
        MutableClock sharedViewInOtherZone = clock.withZone(ZoneOffset.MIN);
        assertEquals(Instant.EPOCH, clock.instant());

        // Advancing the clock moves its instant forward by the same amount.
        clock.add(Duration.ofSeconds(5));
        assertEquals(Instant.EPOCH.plusSeconds(5), clock.instant());

        // Resetting the instant is reflected by the clock and by any view that
        // shares the same underlying instant, regardless of time-zone.
        clock.setInstant(Instant.MIN);
        assertEquals(Instant.MIN, clock.instant());
        assertEquals(Instant.MIN, sharedViewInOtherZone.instant());
    }
}
