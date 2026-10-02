package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_minus_int {

    @Test
    public void test_minus_int() {
        Hours fiveHours = Hours.of(5);

        assertEquals(Hours.of(5), fiveHours.minus(0));
        assertEquals(Hours.of(3), fiveHours.minus(2));
        assertEquals(Hours.of(7), fiveHours.minus(-2));
        // Boundary: subtracting -1 from (MAX_VALUE - 1) reaches MAX_VALUE without overflow
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE - 1).minus(-1));
        // Boundary: subtracting 1 from (MIN_VALUE + 1) reaches MIN_VALUE without overflow
        assertEquals(Hours.of(Integer.MIN_VALUE), Hours.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
