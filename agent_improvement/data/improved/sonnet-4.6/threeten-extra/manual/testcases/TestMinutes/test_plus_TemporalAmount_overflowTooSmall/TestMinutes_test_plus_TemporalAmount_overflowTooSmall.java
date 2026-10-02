package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        // Integer.MIN_VALUE + 1 is the smallest value that can still be decremented by 1.
        // Adding -2 pushes the result below Integer.MIN_VALUE, so Math.addExact must throw.
        assertThrows(ArithmeticException.class, () -> Minutes.of(Integer.MIN_VALUE + 1).plus(Minutes.of(-2)));
    }
}
