package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link MutableClock#of(Instant, java.time.ZoneId)} rejects a
 * null instant argument.
 */
public class TestMutableClock_test_of_nullInstant {

    @Test
    public void of_withNullInstant_throwsNullPointerException() {
        // The instant argument is required; passing null must be rejected.
        assertThrows(
                NullPointerException.class,
                () -> MutableClock.of(null, ZoneOffset.UTC));
    }
}
