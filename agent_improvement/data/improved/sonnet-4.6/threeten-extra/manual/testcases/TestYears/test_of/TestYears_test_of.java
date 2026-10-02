package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TestYears_test_of {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, Integer.MAX_VALUE, -1, -2, Integer.MIN_VALUE})
    public void test_of_getAmountReturnsProvidedValue(int years) {
        assertEquals(years, Years.of(years).getAmount());
    }
}
