package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_instant {

    /**
     * A clock created via epochUTC() starts at the Unix epoch (1970-01-01T00:00:00Z).
     */
    @Test
    public void test_instant_initialValueIsEpoch() {
        MutableClock clock = MutableClock.epochUTC();

        assertEquals(Instant.EPOCH, clock.instant());
    }

    /**
     * Adding a Duration advances the clock's instant by exactly that duration.
     */
    @Test
    public void test_instant_advancesAfterAdd() {
        MutableClock clock = MutableClock.epochUTC();

        clock.add(Duration.ofSeconds(5));

        assertEquals(Instant.EPOCH.plusSeconds(5), clock.instant());
    }

    /**
     * setInstant() replaces the clock's instant with the supplied value regardless
     * of what the previous instant was.
     */
    @Test
    public void test_instant_reflectsSetInstant() {
        MutableClock clock = MutableClock.epochUTC();

        clock.setInstant(Instant.MIN);

        assertEquals(Instant.MIN, clock.instant());
    }

    /**
     * A clock obtained via withZone() shares the same underlying instant holder as
     * the original clock, so mutations applied to either clock are visible through
     * both views.
     */
    @Test
    public void test_instant_sharedAcrossZoneView() {
        MutableClock clock = MutableClock.epochUTC();
        MutableClock zoneView = clock.withZone(ZoneOffset.MIN);

        clock.setInstant(Instant.MIN);

        assertEquals(Instant.MIN, zoneView.instant());
    }
}
