package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // (MAX_VALUE - 1) minus (-2) = MAX_VALUE - 1 + 2 = MAX_VALUE + 1, which overflows int
        assertThrows(ArithmeticException.class, () -> Hours.of(Integer.MAX_VALUE - 1).minus(Hours.of(-2)));
    }
}
