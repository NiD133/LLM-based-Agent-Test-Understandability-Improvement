package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_of {

    @Test
    public void test_of() {
        assertYearsAmount(1, 1);
        assertYearsAmount(2, 2);
        assertYearsAmount(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertYearsAmount(-1, -1);
        assertYearsAmount(-2, -2);
        assertYearsAmount(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    private static void assertYearsAmount(int inputYears, int expectedAmount) {
        assertEquals(expectedAmount, Years.of(inputYears).getAmount());
    }
}
