package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_withZone_null {

    /**
     * Verifies that withZone(null) throws NullPointerException,
     * enforcing the not-null contract documented on MutableClock.withZone(ZoneId).
     */
    @Test
    public void test_withZone_null() {
        MutableClock clock = MutableClock.epochUTC();
        ZoneId nullZone = null;
        assertThrows(NullPointerException.class, () -> clock.withZone(nullZone));
    }
}
