package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        assertThrows(
                ArithmeticException.class,
                () -> Days.of(Integer.MAX_VALUE - 1).plus(Days.of(2)));
    }
}
