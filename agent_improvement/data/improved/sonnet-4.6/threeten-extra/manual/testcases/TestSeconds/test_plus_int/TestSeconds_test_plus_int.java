package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_plus_int {

    @Test
    public void test_plus_int() {
        Seconds fiveSeconds = Seconds.of(5);

        // Adding zero returns the same value unchanged
        assertEquals(Seconds.of(5), fiveSeconds.plus(0));

        // Adding a positive integer increases the total seconds
        assertEquals(Seconds.of(7), fiveSeconds.plus(2));

        // Adding a negative integer decreases the total seconds
        assertEquals(Seconds.of(3), fiveSeconds.plus(-2));

        // Adding 1 to (MAX_VALUE - 1) reaches Integer.MAX_VALUE without overflow
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).plus(1));

        // Adding -1 to (MIN_VALUE + 1) reaches Integer.MIN_VALUE without overflow
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
