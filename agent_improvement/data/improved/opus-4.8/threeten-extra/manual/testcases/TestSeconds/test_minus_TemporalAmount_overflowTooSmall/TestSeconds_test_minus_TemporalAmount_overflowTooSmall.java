package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        // Subtracting 2 from (Integer.MIN_VALUE + 1) underflows below the
        // int range, so minus(TemporalAmount) must report an overflow.
        Seconds nearMinimum = Seconds.of(Integer.MIN_VALUE + 1);
        Seconds amountToSubtract = Seconds.of(2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(amountToSubtract));
    }
}
