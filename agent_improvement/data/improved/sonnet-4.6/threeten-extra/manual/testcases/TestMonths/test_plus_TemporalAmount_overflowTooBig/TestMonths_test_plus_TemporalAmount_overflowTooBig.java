package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestMonths_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        // Adding 2 to (MAX_VALUE - 1) exceeds Integer.MAX_VALUE, so ArithmeticException is expected
        Months nearMax = Months.of(Integer.MAX_VALUE - 1);
        Months addend = Months.of(2);
        assertThrows(ArithmeticException.class, () -> nearMax.plus(addend));
    }
}
