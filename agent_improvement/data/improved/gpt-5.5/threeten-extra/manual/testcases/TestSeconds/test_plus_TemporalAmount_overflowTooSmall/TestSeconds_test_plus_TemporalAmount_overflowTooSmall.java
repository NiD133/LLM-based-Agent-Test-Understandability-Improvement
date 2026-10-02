package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        assertThrows(
                ArithmeticException.class,
                () -> Seconds.of(Integer.MIN_VALUE + 1).plus(Seconds.of(-2)));
    }
}
