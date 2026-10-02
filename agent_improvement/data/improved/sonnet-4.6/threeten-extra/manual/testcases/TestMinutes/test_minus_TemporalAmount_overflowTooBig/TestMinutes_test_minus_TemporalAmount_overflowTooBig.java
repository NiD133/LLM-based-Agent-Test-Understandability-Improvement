package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // Subtracting a negative amount is equivalent to addition.
        // (MAX_VALUE - 1) minus (-2) = MAX_VALUE + 1, which exceeds Integer.MAX_VALUE.
        Minutes nearMax = Minutes.of(Integer.MAX_VALUE - 1);
        Minutes negativeTwo = Minutes.of(-2);
        assertThrows(ArithmeticException.class, () -> nearMax.minus(negativeTwo));
    }
}
