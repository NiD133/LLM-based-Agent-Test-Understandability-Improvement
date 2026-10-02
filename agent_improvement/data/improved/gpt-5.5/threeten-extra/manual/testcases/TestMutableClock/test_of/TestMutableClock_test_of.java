package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_of {

    @Test
    public void test_of() {
        assertInitialInstantIsRetained();
        assertZoneIsRetained();
    }

    private static void assertInitialInstantIsRetained() {
        assertEquals(Instant.EPOCH, MutableClock.of(Instant.EPOCH, ZoneOffset.UTC).instant());
        assertEquals(Instant.MIN, MutableClock.of(Instant.MIN, ZoneOffset.UTC).instant());
        assertEquals(Instant.MAX, MutableClock.of(Instant.MAX, ZoneOffset.UTC).instant());
    }

    private static void assertZoneIsRetained() {
        assertEquals(ZoneOffset.UTC, MutableClock.of(Instant.EPOCH, ZoneOffset.UTC).getZone());
        assertEquals(ZoneOffset.MIN, MutableClock.of(Instant.EPOCH, ZoneOffset.MIN).getZone());
        assertEquals(ZoneOffset.MAX, MutableClock.of(Instant.EPOCH, ZoneOffset.MAX).getZone());
    }
}
