package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#plus(java.time.temporal.TemporalAmount)} when the added
 * temporal amount is itself a {@code Minutes} instance.
 */
public class TestMinutes_test_plus_TemporalAmount_Minutes {

    @Test
    public void test_plus_TemporalAmount_Minutes() {
        Minutes fiveMinutes = Minutes.of(5);

        // Adding zero leaves the amount unchanged.
        assertEquals(Minutes.of(5), fiveMinutes.plus(Minutes.of(0)));

        // Adding a positive amount increases the total.
        assertEquals(Minutes.of(7), fiveMinutes.plus(Minutes.of(2)));

        // Adding a negative amount decreases the total.
        assertEquals(Minutes.of(3), fiveMinutes.plus(Minutes.of(-2)));

        // Adding exactly reaches the int boundaries without overflowing.
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE - 1).plus(Minutes.of(1)));
        assertEquals(Minutes.of(Integer.MIN_VALUE), Minutes.of(Integer.MIN_VALUE + 1).plus(Minutes.of(-1)));
    }
}
