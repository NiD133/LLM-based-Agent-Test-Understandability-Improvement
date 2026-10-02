package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link TaiInstant#plus(Duration)} reports arithmetic overflow
 * when adding a negative duration would push the seconds below {@code Long.MIN_VALUE}.
 */
public class TestTaiInstant_test_plus_overflowTooSmall {

    @Test
    public void test_plus_overflowTooSmall() {
        // Start at the smallest representable instant.
        TaiInstant smallestInstant = TaiInstant.ofTaiSeconds(Long.MIN_VALUE, 0);

        // Subtracting one more second (-1s + 999999999ns) overflows the long second count.
        Duration negativeDuration = Duration.ofSeconds(-1, 999999999);

        assertThrows(ArithmeticException.class, () -> smallestInstant.plus(negativeDuration));
    }
}
