package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestMutableClock_test_of_nullZone {

    @Test
    public void test_of_nullZone() {
        assertThrows(
                NullPointerException.class,
                () -> MutableClock.of(Instant.EPOCH, null));
    }
}
