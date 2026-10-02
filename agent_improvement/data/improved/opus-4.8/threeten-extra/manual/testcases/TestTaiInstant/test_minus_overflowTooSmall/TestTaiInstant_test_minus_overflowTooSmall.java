package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant#minus(Duration)} reports arithmetic overflow
 * when subtracting a duration would push the second count below {@code Long.MIN_VALUE}.
 */
public class TestTaiInstant_test_minus_overflowTooSmall {

    @Test
    public void test_minus_overflowTooSmall() {
        // Start at the smallest representable instant.
        TaiInstant smallestInstant = TaiInstant.ofTaiSeconds(Long.MIN_VALUE, 0);

        // Subtracting even one nanosecond underflows the supported range,
        // so the operation must fail rather than wrap around.
        assertThrows(
                ArithmeticException.class,
                () -> smallestInstant.minus(Duration.ofSeconds(0, 1)));
    }
}
