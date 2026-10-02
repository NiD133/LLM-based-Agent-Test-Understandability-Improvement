package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_hashCode_sameWhenSharedUpdates {

    @Test
    public void test_hashCode_sameWhenSharedUpdates() {
        // Two clocks share the same InstantHolder when one is derived from the
        // other via withZone(). Clocks that share an InstantHolder and have the
        // same zone must produce the same hashCode, regardless of the chain of
        // withZone() calls that produced them.
        MutableClock originalClock = MutableClock.epochUTC();

        // Derive a view with a different zone — shares the same InstantHolder
        MutableClock clockWithMinZone = originalClock.withZone(ZoneOffset.MIN);

        // Derive another view back to UTC — still shares the same InstantHolder
        MutableClock clockWithUtcZone = clockWithMinZone.withZone(ZoneOffset.UTC);

        // originalClock and clockWithUtcZone share the same InstantHolder and
        // use the same zone (UTC), so their hashCodes must be equal.
        assertEquals(originalClock.hashCode(), clockWithUtcZone.hashCode());
    }
}
