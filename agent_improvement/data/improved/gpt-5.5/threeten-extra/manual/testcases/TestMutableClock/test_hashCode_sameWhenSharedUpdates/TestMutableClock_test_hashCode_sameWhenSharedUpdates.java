package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_hashCode_sameWhenSharedUpdates {

    @Test
    public void test_hashCode_sameWhenSharedUpdates() {
        MutableClock originalUtcClock = MutableClock.epochUTC();
        MutableClock sharedClockInDifferentZone = originalUtcClock.withZone(ZoneOffset.MIN);

        MutableClock sharedClockBackInUtc = sharedClockInDifferentZone.withZone(ZoneOffset.UTC);

        assertEquals(originalUtcClock.hashCode(), sharedClockBackInUtc.hashCode());
    }
}
