package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZoneOffset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestMutableClock_test_of_nullInstant {

    @Test
    @DisplayName("MutableClock.of rejects a null instant with NullPointerException")
    public void test_of_nullInstant() {
        // Passing null as the instant argument must be rejected immediately
        assertThrows(NullPointerException.class, () -> MutableClock.of(null, ZoneOffset.UTC));
    }
}
