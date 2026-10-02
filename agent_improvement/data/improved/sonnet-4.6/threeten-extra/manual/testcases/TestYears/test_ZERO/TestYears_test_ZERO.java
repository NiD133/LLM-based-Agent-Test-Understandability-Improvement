package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#ZERO} is the canonical singleton for zero years
 * and that its sign-query methods all agree it is zero.
 */
public class TestYears_test_ZERO {

    @Test
    public void test_ZERO() {
        Years zeroYears = Years.of(0);

        // Years.of(0) must return the interned ZERO singleton, not a new object
        assertSame(Years.ZERO, zeroYears);
        assertEquals(Years.ZERO, zeroYears);

        // The wrapped value must be exactly 0
        assertEquals(0, Years.ZERO.getAmount());

        // Sign predicates: only isZero() should be true for the zero constant
        assertFalse(Years.ZERO.isNegative());
        assertTrue(Years.ZERO.isZero());
        assertFalse(Years.ZERO.isPositive());
    }
}
