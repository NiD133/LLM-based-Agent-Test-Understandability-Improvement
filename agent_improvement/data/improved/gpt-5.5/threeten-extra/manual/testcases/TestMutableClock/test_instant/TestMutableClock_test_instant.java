package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_instant {

    @Test
    public void test_instant() {
        MutableClock clock = MutableClock.epochUTC();
        MutableClock sameInstantInDifferentZone = clock.withZone(ZoneOffset.MIN);

        assertEquals(Instant.EPOCH, clock.instant());

        clock.add(Duration.ofSeconds(5));
        assertEquals(Instant.EPOCH.plusSeconds(5), clock.instant());

        clock.setInstant(Instant.MIN);
        assertEquals(Instant.MIN, clock.instant());
        assertEquals(Instant.MIN, sameInstantInDifferentZone.instant());
    }
}
