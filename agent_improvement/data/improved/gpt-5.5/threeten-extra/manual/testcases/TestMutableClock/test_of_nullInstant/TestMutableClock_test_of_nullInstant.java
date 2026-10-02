package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_of_nullInstant {

    @Test
    public void test_of_nullInstant() {
        assertThrows(NullPointerException.class, () -> MutableClock.of(null, ZoneOffset.UTC));
    }
}
