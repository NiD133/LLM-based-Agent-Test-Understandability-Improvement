package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        assertThrows(ArithmeticException.class, () -> {
            Days amountNearIntegerMinimum = Days.of(Integer.MIN_VALUE + 1);
            Days amountToSubtract = Days.of(2);

            amountNearIntegerMinimum.minus(amountToSubtract);
        });
    }
}
