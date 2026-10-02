package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_plus_TemporalAmount_overflowTooSmall() {
        // Adding -2 to (MIN_VALUE + 1) would push the result below Integer.MIN_VALUE
        Months nearMinValue = Months.of(Integer.MIN_VALUE + 1);
        Months negativeTwo = Months.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMinValue.plus(negativeTwo));
    }
}
