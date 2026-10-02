package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        // Adding -2 to (MIN_VALUE + 1) would produce a value below Integer.MIN_VALUE,
        // so Seconds.plus must throw ArithmeticException instead of silently wrapping.
        assertThrows(ArithmeticException.class, () -> Seconds.of(Integer.MIN_VALUE + 1).plus(Seconds.of(-2)));
    }
}
