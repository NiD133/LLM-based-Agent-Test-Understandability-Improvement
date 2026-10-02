package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_ZERO {

    /**
     * Verifies that {@code Minutes.ZERO} is the canonical singleton for zero minutes:
     * it is the same instance returned by {@code Minutes.of(0)}, reports an amount
     * of 0, and correctly identifies itself as zero (not negative, not positive).
     */
    @Test
    public void test_ZERO() {
        // ZERO is a singleton — of(0) must return the exact same object
        assertSame(Minutes.ZERO, Minutes.of(0));
        assertEquals(Minutes.ZERO, Minutes.of(0));

        // numeric value must be 0
        assertEquals(0, Minutes.ZERO.getAmount());

        // sign predicates must be consistent with a zero amount
        assertFalse(Minutes.ZERO.isNegative());
        assertTrue(Minutes.ZERO.isZero());
        assertFalse(Minutes.ZERO.isPositive());
    }
}
