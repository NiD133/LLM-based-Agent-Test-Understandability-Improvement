package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_getZone {

    @Test
    public void test_getZone() {
        MutableClock epochUtcClock = MutableClock.epochUTC();
        MutableClock sameInstantInMinimumOffset = epochUtcClock.withZone(ZoneOffset.MIN);
        MutableClock epochInMaximumOffset = MutableClock.of(Instant.EPOCH, ZoneOffset.MAX);

        assertEquals(ZoneOffset.UTC, epochUtcClock.getZone());
        assertEquals(ZoneOffset.MIN, sameInstantInMinimumOffset.getZone());
        assertEquals(ZoneOffset.MAX, epochInMaximumOffset.getZone());
    }
}
