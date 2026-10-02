package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link TaiInstant#plus(Duration)} reports overflow when the
 * result would exceed the supported range.
 */
public class TestTaiInstant_test_plus_overflowTooBig {

    @Test
    public void plus_whenResultExceedsMaxRange_throwsArithmeticException() {
        // Start at the largest representable instant.
        TaiInstant maxInstant = TaiInstant.ofTaiSeconds(Long.MAX_VALUE, 999_999_999);

        // Adding even a single nanosecond pushes the value past the range.
        Duration oneNanosecond = Duration.ofSeconds(0, 1);

        assertThrows(ArithmeticException.class, () -> maxInstant.plus(oneNanosecond));
    }
}
