package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_abs {

    @Test
    public void test_abs() {
        assertAbsoluteValueIs(0, 0);
        assertAbsoluteValueIs(12, 12);
        assertAbsoluteValueIs(12, -12);
        assertAbsoluteValueIs(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertAbsoluteValueIs(Integer.MAX_VALUE, -Integer.MAX_VALUE);
    }

    private static void assertAbsoluteValueIs(int expectedYears, int actualYears) {
        assertEquals(Years.of(expectedYears), Years.of(actualYears).abs());
    }
}
