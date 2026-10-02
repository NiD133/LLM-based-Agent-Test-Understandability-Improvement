package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        // Subtracting 2 from (MIN_VALUE + 1) must wrap below Integer.MIN_VALUE
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MIN_VALUE + 1).minus(Minutes.of(2)));
    }
}
