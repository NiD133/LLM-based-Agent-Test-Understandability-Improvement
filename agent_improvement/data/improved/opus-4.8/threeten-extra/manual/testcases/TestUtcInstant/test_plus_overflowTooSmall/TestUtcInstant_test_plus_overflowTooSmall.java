package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link UtcInstant#plus(Duration)} reports arithmetic overflow
 * when the result would fall below the smallest representable instant.
 */
public class TestUtcInstant_test_plus_overflowTooSmall {

    @Test
    public void plus_belowMinModifiedJulianDay_throwsArithmeticException() {
        // Start at the earliest possible Modified Julian Day.
        UtcInstant earliestInstant = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);

        // Subtracting even a single nanosecond overflows past the minimum.
        assertThrows(ArithmeticException.class, () -> earliestInstant.plus(Duration.ofNanos(-1)));
    }
}
