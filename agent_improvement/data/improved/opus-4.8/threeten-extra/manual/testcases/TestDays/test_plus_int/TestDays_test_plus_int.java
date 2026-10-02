package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#plus(int)}, which returns a new {@code Days} whose amount is
 * this amount plus the given number of days.
 */
public class TestDays_test_plus_int {

    @Test
    public void plus_int_addsTheGivenNumberOfDays() {
        Days fiveDays = Days.of(5);

        // Adding zero leaves the amount unchanged.
        assertEquals(Days.of(5), fiveDays.plus(0));

        // Adding a positive amount increases the number of days.
        assertEquals(Days.of(7), fiveDays.plus(2));

        // Adding a negative amount decreases the number of days.
        assertEquals(Days.of(3), fiveDays.plus(-2));

        // Adding is allowed right up to the int boundaries.
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Days.of(Integer.MIN_VALUE), Days.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
