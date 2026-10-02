package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_withZone {

    @Test
    public void test_withZone() {
        MutableClock utcClock = MutableClock.epochUTC();
        MutableClock minOffsetView = utcClock.withZone(ZoneOffset.MIN);
        MutableClock utcView = minOffsetView.withZone(ZoneOffset.UTC);

        utcClock.setInstant(Instant.MIN);

        assertEquals(Instant.MIN, minOffsetView.instant());
        assertEquals(Instant.MIN, utcView.instant());
        assertEquals(ZoneOffset.MIN, minOffsetView.getZone());
        assertEquals(ZoneOffset.UTC, utcView.getZone());
        assertNotEquals(utcClock, minOffsetView);
        assertEquals(utcClock, utcView);
    }
}
