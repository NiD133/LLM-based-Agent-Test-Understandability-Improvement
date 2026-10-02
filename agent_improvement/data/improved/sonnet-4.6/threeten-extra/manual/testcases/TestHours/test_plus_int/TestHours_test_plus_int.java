package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_plus_int {

    @Test
    public void test_plus_int() {
        Hours fiveHours = Hours.of(5);

        // Adding zero returns the same amount unchanged
        assertEquals(Hours.of(5), fiveHours.plus(0), "plus(0) should return the same value");

        // Adding a positive number increases the total
        assertEquals(Hours.of(7), fiveHours.plus(2), "plus(2) should add 2 hours");

        // Adding a negative number decreases the total
        assertEquals(Hours.of(3), fiveHours.plus(-2), "plus(-2) should subtract 2 hours");

        // Adding 1 to MAX_VALUE - 1 reaches the exact maximum without overflow
        assertEquals(Hours.of(Integer.MAX_VALUE),
                Hours.of(Integer.MAX_VALUE - 1).plus(1),
                "plus(1) at MAX_VALUE - 1 should reach Integer.MAX_VALUE");

        // Adding -1 to MIN_VALUE + 1 reaches the exact minimum without overflow
        assertEquals(Hours.of(Integer.MIN_VALUE),
                Hours.of(Integer.MIN_VALUE + 1).plus(-1),
                "plus(-1) at MIN_VALUE + 1 should reach Integer.MIN_VALUE");
    }
}
