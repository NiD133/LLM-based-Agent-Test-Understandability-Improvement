package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        Hours almostMaxValue = Hours.of(Integer.MAX_VALUE - 1);
        Hours twoHours = Hours.of(2);

        assertThrows(ArithmeticException.class, () -> almostMaxValue.plus(twoHours));
    }
}
