package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_minus_int {

    @Test
    public void test_minus_int() {
        Years fiveYears = Years.of(5);

        // subtracting zero returns the same value
        assertEquals(Years.of(5), fiveYears.minus(0));

        // subtracting a positive number decreases the year count
        assertEquals(Years.of(3), fiveYears.minus(2));

        // subtracting a negative number increases the year count
        assertEquals(Years.of(7), fiveYears.minus(-2));

        // boundary: subtracting -1 from MAX_VALUE - 1 reaches MAX_VALUE without overflow
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE - 1).minus(-1));

        // boundary: subtracting 1 from MIN_VALUE + 1 reaches MIN_VALUE without overflow
        assertEquals(Years.of(Integer.MIN_VALUE), Years.of(Integer.MIN_VALUE + 1).minus(1));
    }
}
