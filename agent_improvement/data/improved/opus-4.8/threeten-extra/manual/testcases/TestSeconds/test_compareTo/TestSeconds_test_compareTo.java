package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#compareTo(Seconds)}.
 */
public class TestSeconds_test_compareTo {

    @Test
    public void test_compareTo() {
        Seconds five = Seconds.of(5);
        Seconds six = Seconds.of(6);

        // an amount compared to itself is equal
        assertEquals(0, five.compareTo(five));
        // a smaller amount compared to a larger one is negative
        assertEquals(-1, five.compareTo(six));
        // a larger amount compared to a smaller one is positive
        assertEquals(1, six.compareTo(five));
    }
}
