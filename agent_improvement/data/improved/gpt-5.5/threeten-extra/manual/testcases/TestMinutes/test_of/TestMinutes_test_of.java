package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_of {

    @Test
    public void test_of() {
        assertMinutesAmount(0);
        assertMinutesAmount(1);
        assertMinutesAmount(2);
        assertMinutesAmount(Integer.MAX_VALUE);
        assertMinutesAmount(-1);
        assertMinutesAmount(-2);
        assertMinutesAmount(Integer.MIN_VALUE);
    }

    private static void assertMinutesAmount(int expectedMinutes) {
        assertEquals(expectedMinutes, Minutes.of(expectedMinutes).getAmount());
    }
}
