package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        int minuendNearMinimum = Integer.MIN_VALUE + 1;
        Minutes amountToSubtract = Minutes.of(2);

        assertThrows(
                ArithmeticException.class,
                () -> Minutes.of(minuendNearMinimum).minus(amountToSubtract));
    }
}
