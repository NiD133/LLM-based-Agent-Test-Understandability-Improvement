package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_abs {

    @Test
    public void test_abs() {
        Seconds zero = Seconds.of(0);
        Seconds positive = Seconds.of(12);
        Seconds negative = Seconds.of(-12);
        Seconds maxValue = Seconds.of(Integer.MAX_VALUE);
        Seconds negativeMaxValue = Seconds.of(-Integer.MAX_VALUE);

        assertEquals(zero, zero.abs());
        assertEquals(positive, positive.abs());
        assertEquals(positive, negative.abs());
        assertEquals(maxValue, maxValue.abs());
        assertEquals(maxValue, negativeMaxValue.abs());
    }
}
