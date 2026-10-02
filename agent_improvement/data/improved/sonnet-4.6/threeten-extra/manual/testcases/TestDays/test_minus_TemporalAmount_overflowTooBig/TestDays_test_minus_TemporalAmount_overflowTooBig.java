package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // Subtracting -2 is equivalent to adding 2; (MAX_VALUE - 1) + 2 exceeds int range
        assertThrows(ArithmeticException.class, () -> Days.of(Integer.MAX_VALUE - 1).minus(Days.of(-2)));
    }
}
