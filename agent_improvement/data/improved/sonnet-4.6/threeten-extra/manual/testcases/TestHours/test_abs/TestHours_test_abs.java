package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_abs {

    @Test
    public void test_abs_zero_remainsZero() {
        assertEquals(Hours.of(0), Hours.of(0).abs());
    }

    @Test
    public void test_abs_positiveValue_unchanged() {
        assertEquals(Hours.of(12), Hours.of(12).abs());
    }

    @Test
    public void test_abs_negativeValue_becomesPositive() {
        assertEquals(Hours.of(12), Hours.of(-12).abs());
    }

    @Test
    public void test_abs_maxValue_unchanged() {
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE).abs());
    }

    @Test
    public void test_abs_negativeMaxValue_becomesPositiveMax() {
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(-Integer.MAX_VALUE).abs());
    }
}
