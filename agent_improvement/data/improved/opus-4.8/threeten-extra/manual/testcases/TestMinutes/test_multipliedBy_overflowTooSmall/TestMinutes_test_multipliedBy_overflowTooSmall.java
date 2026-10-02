package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // Doubling a value just below half of Integer.MIN_VALUE underflows the int range,
        // so multipliedBy must signal the overflow with an ArithmeticException.
        Minutes justBelowHalfMinValue = Minutes.of(Integer.MIN_VALUE / 2 - 1);

        assertThrows(ArithmeticException.class, () -> justBelowHalfMinValue.multipliedBy(2));
    }
}
