package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_negated {

    @Test
    public void test_negated() {
        assertNegatedYears(0, 0);
        assertNegatedYears(12, -12);
        assertNegatedYears(-12, 12);
        assertNegatedYears(Integer.MAX_VALUE, -Integer.MAX_VALUE);
    }

    private static void assertNegatedYears(int inputYears, int expectedYears) {
        assertEquals(Years.of(expectedYears), Years.of(inputYears).negated());
    }
}
