package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(
                ArithmeticException.class,
                () -> Months.of(Integer.MIN_VALUE + 1).minus(Months.of(2)));
    }
}
