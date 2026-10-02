package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_of {

    @Test
    public void test_of() {
        assertDaysAmount(0, 0);
        assertDaysAmount(1, 1);
        assertDaysAmount(2, 2);
        assertDaysAmount(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertDaysAmount(-1, -1);
        assertDaysAmount(-2, -2);
        assertDaysAmount(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    private static void assertDaysAmount(int expectedAmount, int inputDays) {
        assertEquals(expectedAmount, Days.of(inputDays).getAmount());
    }
}
