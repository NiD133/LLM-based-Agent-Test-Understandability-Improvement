package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_minus_TemporalAmount_overflowTooSmall {

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        Seconds nearlyMinimumSeconds = Seconds.of(Integer.MIN_VALUE + 1);
        Seconds secondsToSubtract = Seconds.of(2);

        assertThrows(
                ArithmeticException.class,
                () -> nearlyMinimumSeconds.minus(secondsToSubtract));
    }
}
