package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_plus_int {

    @Test
    public void test_plus_int() {
        Days test5 = Days.of(5);

        // Adding zero returns the same value unchanged
        assertEquals(Days.of(5), test5.plus(0));

        // Adding a positive number increases the day count
        assertEquals(Days.of(7), test5.plus(2));

        // Adding a negative number decreases the day count
        assertEquals(Days.of(3), test5.plus(-2));

        // Adding 1 to MAX_VALUE - 1 reaches Integer.MAX_VALUE without overflow
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).plus(1));

        // Adding -1 to MIN_VALUE + 1 reaches Integer.MIN_VALUE without overflow
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
