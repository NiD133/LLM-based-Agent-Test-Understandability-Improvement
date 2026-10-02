package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_plus_overflowTooSmall {

    // Adding a negative duration to the earliest representable UtcInstant must
    // throw ArithmeticException because the result cannot be represented.
    @Test
    public void test_plus_overflowTooSmall() {
        UtcInstant earliest = UtcInstant.ofModifiedJulianDay(Long.MIN_VALUE, 0);
        assertThrows(ArithmeticException.class, () -> earliest.plus(Duration.ofNanos(-1)));
    }
}
