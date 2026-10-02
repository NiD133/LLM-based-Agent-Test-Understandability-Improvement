package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#plus(java.time.temporal.TemporalAmount)} when the amount
 * added is itself an {@code Hours} value.
 */
public class TestHours_test_plus_TemporalAmount_Hours {

    @Test
    public void test_plus_TemporalAmount_Hours() {
        Hours fiveHours = Hours.of(5);

        // Adding zero leaves the value unchanged.
        assertEquals(Hours.of(5), fiveHours.plus(Hours.of(0)));
        // Adding a positive amount increases the value.
        assertEquals(Hours.of(7), fiveHours.plus(Hours.of(2)));
        // Adding a negative amount decreases the value.
        assertEquals(Hours.of(3), fiveHours.plus(Hours.of(-2)));

        // Adding is allowed right up to the int boundaries without overflowing.
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE - 1).plus(Hours.of(1)));
        assertEquals(Hours.of(Integer.MIN_VALUE), Hours.of(Integer.MIN_VALUE + 1).plus(Hours.of(-1)));
    }
}
