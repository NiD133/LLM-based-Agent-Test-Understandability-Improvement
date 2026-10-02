package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestHours_test_of {

    @Test
    public void test_of_zero_returnsZeroAmount() {
        assertEquals(0, Hours.of(0).getAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void test_of_positiveValues_returnsCorrectAmount(int hours) {
        assertEquals(hours, Hours.of(hours).getAmount());
    }

    @Test
    public void test_of_intMaxValue_returnsIntMaxValue() {
        assertEquals(Integer.MAX_VALUE, Hours.of(Integer.MAX_VALUE).getAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2})
    public void test_of_negativeValues_returnsCorrectAmount(int hours) {
        assertEquals(hours, Hours.of(hours).getAmount());
    }

    @Test
    public void test_of_intMinValue_returnsIntMinValue() {
        assertEquals(Integer.MIN_VALUE, Hours.of(Integer.MIN_VALUE).getAmount());
    }
}
