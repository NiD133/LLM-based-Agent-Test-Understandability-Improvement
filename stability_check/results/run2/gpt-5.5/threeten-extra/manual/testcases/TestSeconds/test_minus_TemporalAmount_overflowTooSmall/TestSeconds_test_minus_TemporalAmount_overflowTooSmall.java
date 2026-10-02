package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> {
            Seconds valueJustAboveMinimum = Seconds.of(Integer.MIN_VALUE + 1);
            Seconds amountToSubtract = Seconds.of(2);

            valueJustAboveMinimum.minus(amountToSubtract);
        });
    }
}
