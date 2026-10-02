package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the properties of the {@link Weeks#ZERO} constant.
 */
public class TestWeeks_test_ZERO {

    @Test
    public void test_ZERO() {
        // Weeks.of(0) must return the cached ZERO singleton, not just an equal instance.
        assertSame(Weeks.ZERO, Weeks.of(0));
        assertEquals(Weeks.ZERO, Weeks.of(0));

        // ZERO holds an amount of exactly zero weeks.
        assertEquals(0, Weeks.ZERO.getAmount());

        // Zero is neither negative nor positive, but it is zero.
        assertFalse(Weeks.ZERO.isNegative());
        assertTrue(Weeks.ZERO.isZero());
        assertFalse(Weeks.ZERO.isPositive());
    }
}
