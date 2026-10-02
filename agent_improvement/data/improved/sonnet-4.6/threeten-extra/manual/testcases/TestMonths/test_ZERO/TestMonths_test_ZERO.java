package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestMonths_test_ZERO {

    /**
     * Verifies that {@link Months#ZERO} is the canonical singleton for zero months
     * and that its sign-query methods all report the correct state (not negative,
     * is zero, not positive).
     */
    @Test
    public void test_ZERO() {
        // ZERO is a singleton: Months.of(0) must return the exact same instance
        assertSame(Months.ZERO, Months.of(0));
        assertEquals(Months.ZERO, Months.of(0));

        // The underlying amount is 0
        assertEquals(0, Months.ZERO.getAmount());

        // Sign checks: zero is neither negative nor positive
        assertFalse(Months.ZERO.isNegative());
        assertTrue(Months.ZERO.isZero());
        assertFalse(Months.ZERO.isPositive());
    }
}
