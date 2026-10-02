package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_setInstant {

    @Test
    public void test_setInstant() {
        MutableClock clock = MutableClock.epochUTC();

        assertEquals(Instant.EPOCH, clock.instant());

        clock.setInstant(Instant.MIN);
        assertEquals(Instant.MIN, clock.instant());

        clock.setInstant(Instant.MAX);
        assertEquals(Instant.MAX, clock.instant());

        clock.setInstant(Instant.EPOCH.plusSeconds(10));
        assertEquals(Instant.EPOCH.plusSeconds(10), clock.instant());
    }
}
