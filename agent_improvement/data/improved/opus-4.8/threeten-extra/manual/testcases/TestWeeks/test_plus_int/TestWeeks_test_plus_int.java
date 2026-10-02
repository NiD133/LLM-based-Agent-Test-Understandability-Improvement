package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#plus(int)}, which returns a new {@code Weeks} whose amount
 * is this amount plus the given number of weeks.
 */
public class TestWeeks_test_plus_int {

    @Test
    public void plus_int_addsTheGivenNumberOfWeeks() {
        Weeks fiveWeeks = Weeks.of(5);

        // Adding zero leaves the amount unchanged.
        assertEquals(Weeks.of(5), fiveWeeks.plus(0));
        // Adding a positive number increases the amount.
        assertEquals(Weeks.of(7), fiveWeeks.plus(2));
        // Adding a negative number decreases the amount.
        assertEquals(Weeks.of(3), fiveWeeks.plus(-2));

        // Adding up to the integer bounds succeeds without overflow.
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Weeks.of(Integer.MIN_VALUE), Weeks.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
