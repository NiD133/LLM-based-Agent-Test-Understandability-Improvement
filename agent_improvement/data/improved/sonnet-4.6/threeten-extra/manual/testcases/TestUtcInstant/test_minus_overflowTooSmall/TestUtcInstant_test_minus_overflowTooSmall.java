package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_minus_overflowTooSmall {

    @Test
    public void test_minus_overflowTooSmall() {
        // Subtracting any duration from the minimum representable UtcInstant must
        // throw ArithmeticException because the underlying TAI conversion overflows.
        UtcInstant minInstant = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> minInstant.minus(Duration.ofNanos(1)));
    }
}
