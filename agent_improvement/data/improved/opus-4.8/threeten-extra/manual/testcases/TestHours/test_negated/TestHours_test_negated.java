package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#negated()}, which flips the sign of the hour amount.
 */
public class TestHours_test_negated {

    @Test
    public void test_negated() {
        // Negating zero leaves it unchanged.
        assertEquals(Hours.of(0), Hours.of(0).negated());

        // Negating a positive amount yields its negative counterpart.
        assertEquals(Hours.of(-12), Hours.of(12).negated());

        // Negating a negative amount yields its positive counterpart.
        assertEquals(Hours.of(12), Hours.of(-12).negated());

        // Negating the largest int amount yields its negative counterpart.
        assertEquals(Hours.of(-Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE).negated());
    }
}
