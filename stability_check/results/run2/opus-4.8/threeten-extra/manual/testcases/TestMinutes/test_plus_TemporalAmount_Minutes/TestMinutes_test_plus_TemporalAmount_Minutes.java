package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#plus(java.time.temporal.TemporalAmount)} when the added
 * amount is itself a {@code Minutes} value.
 */
public class TestMinutes_test_plus_TemporalAmount_Minutes {

    @Test
    public void plus_addsMinutesTemporalAmount() {
        Minutes fiveMinutes = Minutes.of(5);

        // Adding zero leaves the value unchanged.
        assertEquals(Minutes.of(5), fiveMinutes.plus(Minutes.of(0)));

        // Adding a positive amount increases the value.
        assertEquals(Minutes.of(7), fiveMinutes.plus(Minutes.of(2)));

        // Adding a negative amount decreases the value.
        assertEquals(Minutes.of(3), fiveMinutes.plus(Minutes.of(-2)));

        // Adding is allowed right up to the int boundaries without overflow.
        assertEquals(Minutes.of(Integer.MAX_VALUE), Minutes.of(Integer.MAX_VALUE - 1).plus(Minutes.of(1)));
        assertEquals(Minutes.of(Integer.MIN_VALUE), Minutes.of(Integer.MIN_VALUE + 1).plus(Minutes.of(-1)));
    }
}
