package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#multipliedBy(int)}.
 */
public class TestSeconds_test_multipliedBy {

    /**
     * Multiplying a {@code Seconds} amount by a scalar should scale the number of
     * seconds accordingly, including for the zero, identity, and negative cases.
     */
    @Test
    public void test_multipliedBy() {
        Seconds fiveSeconds = Seconds.of(5);

        // Multiplying by zero yields zero seconds.
        assertEquals(Seconds.of(0), fiveSeconds.multipliedBy(0));
        // Multiplying by one leaves the amount unchanged.
        assertEquals(Seconds.of(5), fiveSeconds.multipliedBy(1));
        // Multiplying by a positive scalar scales the amount up.
        assertEquals(Seconds.of(10), fiveSeconds.multipliedBy(2));
        assertEquals(Seconds.of(15), fiveSeconds.multipliedBy(3));
        // Multiplying by a negative scalar flips the sign.
        assertEquals(Seconds.of(-15), fiveSeconds.multipliedBy(-3));
    }
}
