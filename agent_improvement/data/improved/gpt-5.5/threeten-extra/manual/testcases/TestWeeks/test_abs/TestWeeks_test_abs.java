package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_abs {

    @Test
    public void test_abs() {
        assertAbsoluteWeeks(0, 0);
        assertAbsoluteWeeks(12, 12);
        assertAbsoluteWeeks(12, -12);
        assertAbsoluteWeeks(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertAbsoluteWeeks(Integer.MAX_VALUE, -Integer.MAX_VALUE);
    }

    private static void assertAbsoluteWeeks(int expectedWeeks, int inputWeeks) {
        assertEquals(Weeks.of(expectedWeeks), Weeks.of(inputWeeks).abs());
    }
}
