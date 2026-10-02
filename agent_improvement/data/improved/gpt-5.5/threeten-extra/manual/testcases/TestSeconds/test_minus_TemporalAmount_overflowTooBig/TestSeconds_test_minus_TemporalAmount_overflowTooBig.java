package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        Seconds nearMaximum = Seconds.of(Integer.MAX_VALUE - 1);
        Seconds negativeTwoSeconds = Seconds.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMaximum.minus(negativeTwoSeconds));
    }
}
