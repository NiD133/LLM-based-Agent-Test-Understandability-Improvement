package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#minus(java.time.temporal.TemporalAmount)} when the
 * subtracted amount is itself a {@code Minutes} value.
 */
public class TestMinutes_test_minus_TemporalAmount_Minutes {

    @Test
    public void test_minus_TemporalAmount_Minutes() {
        Minutes five = Minutes.of(5);

        // Subtracting zero leaves the value unchanged.
        assertEquals(Minutes.of(5), five.minus(Minutes.of(0)));

        // Subtracting a positive amount decreases the value: 5 - 2 = 3.
        assertEquals(Minutes.of(3), five.minus(Minutes.of(2)));

        // Subtracting a negative amount increases the value: 5 - (-2) = 7.
        assertEquals(Minutes.of(7), five.minus(Minutes.of(-2)));

        // Subtracting -1 from MAX_VALUE - 1 reaches MAX_VALUE without overflow.
        assertEquals(
                Minutes.of(Integer.MAX_VALUE),
                Minutes.of(Integer.MAX_VALUE - 1).minus(Minutes.of(-1)));

        // Subtracting 1 from MIN_VALUE + 1 reaches MIN_VALUE without overflow.
        assertEquals(
                Minutes.of(Integer.MIN_VALUE),
                Minutes.of(Integer.MIN_VALUE + 1).minus(Minutes.of(1)));
    }
}
