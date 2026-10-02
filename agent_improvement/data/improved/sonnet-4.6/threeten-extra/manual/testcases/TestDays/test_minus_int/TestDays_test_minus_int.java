package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_int {

    @Test
    public void test_minus_int() {
        Days test5 = Days.of(5);

        // Subtracting zero returns the same value
        assertEquals(Days.of(5), test5.minus(0),
                "5 - 0 should equal 5");

        // Subtracting a positive number reduces the count
        assertEquals(Days.of(3), test5.minus(2),
                "5 - 2 should equal 3");

        // Subtracting a negative number increases the count
        assertEquals(Days.of(7), test5.minus(-2),
                "5 - (-2) should equal 7");

        // Subtracting -1 from MAX_VALUE - 1 should reach MAX_VALUE without overflow
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).minus(-1),
                "(MAX_VALUE - 1) - (-1) should equal MAX_VALUE");

        // Subtracting 1 from MIN_VALUE + 1 should reach MIN_VALUE without overflow
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).minus(1),
                "(MIN_VALUE + 1) - 1 should equal MIN_VALUE");
    }
}
