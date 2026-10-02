package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        // Subtracting 2 from (MIN_VALUE + 1) would produce MIN_VALUE - 1, which underflows int
        Months nearMinValue = Months.of(Integer.MIN_VALUE + 1);
        Months amountToSubtract = Months.of(2);

        assertThrows(ArithmeticException.class, () -> nearMinValue.minus(amountToSubtract));
    }
}
