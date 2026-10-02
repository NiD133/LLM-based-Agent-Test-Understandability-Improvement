package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hours#of(int)} with a positive value of one
 * reports its amount and sign-related state correctly.
 */
public class TestHours_test_ofPlusOne {

    @Test
    public void test_ofPlusOne() {
        Hours oneHour = Hours.of(1);

        // The stored amount equals the value passed to of(...).
        assertEquals(1, oneHour.getAmount());

        // A value of +1 is positive, so it is neither negative nor zero.
        assertFalse(oneHour.isNegative());
        assertFalse(oneHour.isZero());
        assertTrue(oneHour.isPositive());
    }
}
