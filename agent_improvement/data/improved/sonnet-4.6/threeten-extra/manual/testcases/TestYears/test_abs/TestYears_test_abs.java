package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestYears_test_abs {

    @Test
    public void test_abs_zero_remainsZero() {
        assertEquals(Years.of(0), Years.of(0).abs());
    }

    @Test
    public void test_abs_positiveValue_unchanged() {
        assertEquals(Years.of(12), Years.of(12).abs());
    }

    @Test
    public void test_abs_negativeValue_becomesPositive() {
        assertEquals(Years.of(12), Years.of(-12).abs());
    }

    @Test
    public void test_abs_intMaxValue_unchanged() {
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE).abs());
    }

    @Test
    public void test_abs_negativeIntMaxValue_becomesPositive() {
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(-Integer.MAX_VALUE).abs());
    }
}
