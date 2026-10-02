package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#plus(int)}, which returns a new {@code Weeks} whose
 * amount is this amount plus the given number of weeks.
 */
public class TestWeeks_test_plus_int {

    @Test
    public void test_plus_int() {
        Weeks fiveWeeks = Weeks.of(5);

        // Adding zero leaves the amount unchanged.
        assertEquals(Weeks.of(5), fiveWeeks.plus(0));

        // Adding a positive amount increases the number of weeks: 5 + 2 = 7.
        assertEquals(Weeks.of(7), fiveWeeks.plus(2));

        // Adding a negative amount decreases the number of weeks: 5 + (-2) = 3.
        assertEquals(Weeks.of(3), fiveWeeks.plus(-2));

        // Adding is allowed right up to the int boundaries without overflowing.
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
