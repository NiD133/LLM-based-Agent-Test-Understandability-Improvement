package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // Subtracting a negative amount is equivalent to addition.
        // MAX_VALUE - 1 minus (-2) would require MAX_VALUE + 1, which overflows int.
        Weeks nearMaxValue = Weeks.of(Integer.MAX_VALUE - 1);
        Weeks negativeTwo = Weeks.of(-2);
        assertThrows(ArithmeticException.class, () -> nearMaxValue.minus(negativeTwo));
    }
}
