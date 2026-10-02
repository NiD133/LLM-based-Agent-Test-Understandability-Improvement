package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestWeeks_test_of {

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    public void test_of_positiveValues_returnsCorrectAmount(int weeks) {
        assertEquals(weeks, Weeks.of(weeks).getAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2})
    public void test_of_negativeValues_returnsCorrectAmount(int weeks) {
        assertEquals(weeks, Weeks.of(weeks).getAmount());
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MAX_VALUE, Integer.MIN_VALUE})
    public void test_of_boundaryValues_returnsCorrectAmount(int weeks) {
        assertEquals(weeks, Weeks.of(weeks).getAmount());
    }
}
