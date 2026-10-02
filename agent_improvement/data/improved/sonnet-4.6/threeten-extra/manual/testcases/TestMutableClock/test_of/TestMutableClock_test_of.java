package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_of {

    @Test
    public void test_of_instantIsEpoch_returnsEpochInstant() {
        MutableClock clock = MutableClock.of(Instant.EPOCH, ZoneOffset.UTC);
        assertEquals(Instant.EPOCH, clock.instant());
    }

    @Test
    public void test_of_instantIsMin_returnsMinInstant() {
        MutableClock clock = MutableClock.of(Instant.MIN, ZoneOffset.UTC);
        assertEquals(Instant.MIN, clock.instant());
    }

    @Test
    public void test_of_instantIsMax_returnsMaxInstant() {
        MutableClock clock = MutableClock.of(Instant.MAX, ZoneOffset.UTC);
        assertEquals(Instant.MAX, clock.instant());
    }

    @Test
    public void test_of_zoneIsUTC_returnsUTCZone() {
        MutableClock clock = MutableClock.of(Instant.EPOCH, ZoneOffset.UTC);
        assertEquals(ZoneOffset.UTC, clock.getZone());
    }

    @Test
    public void test_of_zoneIsMin_returnsMinZone() {
        MutableClock clock = MutableClock.of(Instant.EPOCH, ZoneOffset.MIN);
        assertEquals(ZoneOffset.MIN, clock.getZone());
    }

    @Test
    public void test_of_zoneIsMax_returnsMaxZone() {
        MutableClock clock = MutableClock.of(Instant.EPOCH, ZoneOffset.MAX);
        assertEquals(ZoneOffset.MAX, clock.getZone());
    }
}
