package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestDays_test_of {

    // Days.of(n).getAmount() must round-trip: the stored amount equals the input
    @ParameterizedTest(name = "Days.of({0}).getAmount() == {0}")
    @ValueSource(ints = {0, 1, 2, -1, -2})
    public void test_of_preservesAmount(int days) {
        assertEquals(days, Days.of(days).getAmount());
    }

    @Test
    public void test_of_withIntegerMaxValue() {
        assertEquals(Integer.MAX_VALUE, Days.of(Integer.MAX_VALUE).getAmount());
    }

    @Test
    public void test_of_withIntegerMinValue() {
        assertEquals(Integer.MIN_VALUE, Days.of(Integer.MIN_VALUE).getAmount());
    }
}
