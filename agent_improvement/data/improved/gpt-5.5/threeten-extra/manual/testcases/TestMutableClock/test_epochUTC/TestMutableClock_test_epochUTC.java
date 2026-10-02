package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_epochUTC {

    @Test
    public void test_epochUTC() {
        MutableClock clock = MutableClock.epochUTC();

        assertEquals(Instant.EPOCH, clock.instant());
        assertEquals(ZoneOffset.UTC, clock.getZone());
    }
}
