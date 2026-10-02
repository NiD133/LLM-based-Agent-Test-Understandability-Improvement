package org.threeten.extra;

import static java.time.Instant.EPOCH;
import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_epochUTC {

    @Test
    public void test_epochUTC() {
        assertEquals(EPOCH, MutableClock.epochUTC().instant());
        assertEquals(UTC, MutableClock.epochUTC().getZone());
    }
}
