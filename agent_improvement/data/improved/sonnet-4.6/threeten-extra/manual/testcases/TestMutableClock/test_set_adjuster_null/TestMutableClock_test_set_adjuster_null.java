package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMutableClock_test_set_adjuster_null {

    @Test
    @DisplayName("set(null) throws NullPointerException")
    public void test_set_adjuster_null() {
        MutableClock clock = MutableClock.epochUTC();
        assertThrows(NullPointerException.class, () -> clock.set(null));
    }
}
