package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestHours_test_ZERO {

    // Verifies that Hours.ZERO is the canonical singleton for zero hours
    // and that its sign-query methods return consistent results.
    @Test
    public void test_ZERO() {
        // Hours.of(0) must return the same singleton instance as Hours.ZERO
        assertSame(Hours.ZERO, Hours.of(0));
        assertEquals(Hours.ZERO, Hours.of(0));

        // The underlying value must be exactly 0
        assertEquals(0, Hours.ZERO.getAmount());

        // Sign predicates: zero is neither negative nor positive
        assertFalse(Hours.ZERO.isNegative());
        assertTrue(Hours.ZERO.isZero());
        assertFalse(Hours.ZERO.isPositive());
    }
}
