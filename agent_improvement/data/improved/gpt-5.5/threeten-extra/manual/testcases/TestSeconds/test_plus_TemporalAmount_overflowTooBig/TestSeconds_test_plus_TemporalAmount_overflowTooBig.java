package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        Seconds almostMaximumSeconds = Seconds.of(Integer.MAX_VALUE - 1);
        Seconds secondsThatExceedIntegerRange = Seconds.of(2);

        assertThrows(
                ArithmeticException.class,
                () -> almostMaximumSeconds.plus(secondsThatExceedIntegerRange));
    }
}
