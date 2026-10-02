package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_negated {

    @Test
    public void test_negated() {
        assertNegated(0, 0);
        assertNegated(12, -12);
        assertNegated(-12, 12);
        assertNegated(Integer.MAX_VALUE, -Integer.MAX_VALUE);
    }

    private static void assertNegated(int originalMinutes, int expectedMinutes) {
        assertEquals(Minutes.of(expectedMinutes), Minutes.of(originalMinutes).negated());
    }
}
