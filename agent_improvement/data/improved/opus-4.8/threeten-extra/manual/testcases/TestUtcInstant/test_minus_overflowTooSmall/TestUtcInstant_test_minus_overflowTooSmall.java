package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link UtcInstant#minus(Duration)} reports arithmetic overflow
 * when subtracting from the smallest representable instant.
 */
public class TestUtcInstant_test_minus_overflowTooSmall {

    @Test
    public void test_minus_overflowTooSmall() {
        // The earliest possible instant: subtracting any positive duration must overflow.
        UtcInstant earliestInstant = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);

        assertThrows(ArithmeticException.class, () -> earliestInstant.minus(Duration.ofNanos(1)));
    }
}
