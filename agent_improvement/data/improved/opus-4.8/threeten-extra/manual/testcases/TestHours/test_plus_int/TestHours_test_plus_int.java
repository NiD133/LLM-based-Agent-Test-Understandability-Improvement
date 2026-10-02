package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#plus(int)}, which returns a new {@code Hours} whose amount
 * is this amount plus the supplied number of hours.
 */
public class TestHours_test_plus_int {

    @Test
    public void test_plus_int() {
        Hours fiveHours = Hours.of(5);

        // Adding zero leaves the amount unchanged.
        assertEquals(Hours.of(5), fiveHours.plus(0));

        // Adding a positive amount increases the total.
        assertEquals(Hours.of(7), fiveHours.plus(2));

        // Adding a negative amount decreases the total.
        assertEquals(Hours.of(3), fiveHours.plus(-2));

        // Adding right up to the int bounds is allowed (no overflow).
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Hours.of(Integer.MIN_VALUE), Hours.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
