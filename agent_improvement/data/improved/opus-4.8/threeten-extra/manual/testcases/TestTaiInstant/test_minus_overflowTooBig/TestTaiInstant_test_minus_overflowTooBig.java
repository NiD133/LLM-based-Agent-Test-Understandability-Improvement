package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant#minus(Duration)} reports overflow when the
 * subtraction would push the second count beyond the supported range.
 */
public class TestTaiInstant_test_minus_overflowTooBig {

    @Test
    public void test_minus_overflowTooBig() {
        // Start at the largest representable instant.
        TaiInstant maxInstant = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999999999);

        // Subtracting a duration with -1 seconds effectively adds a second,
        // pushing the second count past Long.MAX_VALUE and overflowing.
        Duration durationThatOverflows = Duration.ofSeconds(-1, 999999999);

        assertThrows(ArithmeticException.class, () -> maxInstant.minus(durationThatOverflows));
    }
}
