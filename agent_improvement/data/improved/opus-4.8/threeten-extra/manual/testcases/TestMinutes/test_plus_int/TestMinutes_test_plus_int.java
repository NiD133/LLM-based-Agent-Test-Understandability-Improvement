package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#plus(int)}, which adds a number of minutes
 * to an existing {@code Minutes} amount.
 */
public class TestMinutes_test_plus_int {

    @Test
    public void test_plus_int() {
        Minutes fiveMinutes = Minutes.of(5);

        // Adding zero leaves the amount unchanged.
        assertEquals(Minutes.of(5), fiveMinutes.plus(0));
        // Adding a positive amount increases the total.
        assertEquals(Minutes.of(7), fiveMinutes.plus(2));
        // Adding a negative amount decreases the total.
        assertEquals(Minutes.of(3), fiveMinutes.plus(-2));

        // Adding up to the int boundaries is allowed (no overflow).
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE - 1).plus(1));
        assertEquals(Minutes.of(Integer.MIN_VALUE), Minutes.of(Integer.MIN_VALUE + 1).plus(-1));
    }
}
