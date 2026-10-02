package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        // Subtracting 2 from (MIN_VALUE + 1) would push the result below Integer.MIN_VALUE,
        // which must be detected and reported as an arithmetic overflow.
        Seconds nearMinimum = Seconds.of(Integer.MIN_VALUE + 1);
        Seconds subtrahend = Seconds.of(2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(subtrahend));
    }
}
