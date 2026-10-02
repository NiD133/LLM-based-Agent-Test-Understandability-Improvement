package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // Subtracting a negative amount is equivalent to addition; adding 2 to (MAX_VALUE - 1) overflows int
        Months nearMax = Months.of(Integer.MAX_VALUE - 1);
        Months negativeTwo = Months.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMax.minus(negativeTwo));
    }
}
