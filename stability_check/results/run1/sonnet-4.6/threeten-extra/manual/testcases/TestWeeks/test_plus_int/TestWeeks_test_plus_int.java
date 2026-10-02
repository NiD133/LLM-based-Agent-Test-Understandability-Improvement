package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_plus_int {

    @Test
    public void test_plus_int() {
        Weeks fiveWeeks = Weeks.of(5);

        // Adding zero returns the same value unchanged
        assertEquals(Weeks.of(5), fiveWeeks.plus(0));

        // Adding a positive integer increases the week count
        assertEquals(Weeks.of(7), fiveWeeks.plus(2));

        // Adding a negative integer decreases the week count
        assertEquals(Weeks.of(3), fiveWeeks.plus(-2));

        // Adding 1 to (MAX_VALUE - 1) reaches Integer.MAX_VALUE without overflow
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).plus(1));

        // Adding -1 to (MIN_VALUE + 1) reaches Integer.MIN_VALUE without overflow
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
