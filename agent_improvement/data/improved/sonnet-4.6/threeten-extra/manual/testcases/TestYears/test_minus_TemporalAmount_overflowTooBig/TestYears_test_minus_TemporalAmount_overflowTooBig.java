package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // Subtracting a negative amount is equivalent to addition.
        // (MAX_VALUE - 1) - (-2) = MAX_VALUE + 1, which overflows int.
        Years nearMax = Years.of(Integer.MAX_VALUE - 1);
        Years negativeTwo = Years.of(-2);
        assertThrows(ArithmeticException.class, () -> nearMax.minus(negativeTwo));
    }
}
