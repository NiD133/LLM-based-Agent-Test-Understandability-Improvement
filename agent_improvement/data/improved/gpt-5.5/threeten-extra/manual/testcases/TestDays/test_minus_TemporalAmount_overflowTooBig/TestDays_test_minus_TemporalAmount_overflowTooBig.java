package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        Days nearMaximumDays = Days.of(Integer.MAX_VALUE - 1);
        Days negativeDaysToSubtract = Days.of(-2);

        assertThrows(
                ArithmeticException.class,
                () -> nearMaximumDays.minus(negativeDaysToSubtract));
    }
}
