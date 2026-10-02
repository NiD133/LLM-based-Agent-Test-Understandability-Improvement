package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_of {

    @Test
    public void test_of() {
        assertSecondsAmount(0, 0);
        assertSecondsAmount(1, 1);
        assertSecondsAmount(2, 2);
        assertSecondsAmount(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertSecondsAmount(-1, -1);
        assertSecondsAmount(-2, -2);
        assertSecondsAmount(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    private static void assertSecondsAmount(int expectedAmount, int seconds) {
        assertEquals(expectedAmount, Seconds.of(seconds).getAmount());
    }
}
