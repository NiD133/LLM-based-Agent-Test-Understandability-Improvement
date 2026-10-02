package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#negated()}, which returns a copy of the amount with its sign reversed.
 */
public class TestSeconds_test_negated {

    @Test
    public void test_negated() {
        // Negating zero leaves it unchanged.
        assertEquals(Seconds.of(0), Seconds.of(0).negated());
        // A positive amount becomes negative.
        assertEquals(Seconds.of(-12), Seconds.of(12).negated());
        // A negative amount becomes positive.
        assertEquals(Seconds.of(12), Seconds.of(-12).negated());
        // The largest positive value negates to its negative counterpart.
        assertEquals(Seconds.of(-Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE).negated());
    }
}
