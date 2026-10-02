package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Minutes#multipliedBy(int)}.
 */
public class TestMinutes_test_multipliedBy {

    @Test
    public void test_multipliedBy() {
        Minutes fiveMinutes = Minutes.of(5);

        // Multiplying by zero yields zero minutes.
        assertEquals(Minutes.of(0), fiveMinutes.multipliedBy(0));
        // Multiplying by one leaves the amount unchanged.
        assertEquals(Minutes.of(5), fiveMinutes.multipliedBy(1));
        // Multiplying by a positive scalar scales the amount up.
        assertEquals(Minutes.of(10), fiveMinutes.multipliedBy(2));
        assertEquals(Minutes.of(15), fiveMinutes.multipliedBy(3));
        // Multiplying by a negative scalar flips the sign.
        assertEquals(Minutes.of(-15), fiveMinutes.multipliedBy(-3));
    }
}
