package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_getZone {

    @Test
    public void test_getZone_epochUTC_returnsUTC() {
        MutableClock clock = MutableClock.epochUTC();
        assertEquals(ZoneOffset.UTC, clock.getZone());
    }

    @Test
    public void test_getZone_withZone_returnsNewZone() {
        MutableClock clock = MutableClock.epochUTC();
        MutableClock clockWithMinZone = clock.withZone(ZoneOffset.MIN);
        assertEquals(ZoneOffset.MIN, clockWithMinZone.getZone());
    }

    @Test
    public void test_getZone_ofWithExplicitZone_returnsSpecifiedZone() {
        MutableClock clock = MutableClock.of(Instant.EPOCH, ZoneOffset.MAX);
        assertEquals(ZoneOffset.MAX, clock.getZone());
    }
}
