package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_int {

    @Test
    public void test_plus_int() {
        Hours fiveHours = Hours.of(5);

        assertEquals(Hours.of(5), fiveHours.plus(0));
        assertEquals(Hours.of(7), fiveHours.plus(2));
        assertEquals(Hours.of(3), fiveHours.plus(-2));

        Hours justBelowMaximum = Hours.of(Integer.MAX_VALUE - 1);
        assertEquals(Hours.of(Integer.MAX_VALUE), justBelowMaximum.plus(1));

        Hours justAboveMinimum = Hours.of(Integer.MIN_VALUE + 1);
        assertEquals(Hours.of(Integer.MIN_VALUE), justAboveMinimum.plus(-1));
    }
}
