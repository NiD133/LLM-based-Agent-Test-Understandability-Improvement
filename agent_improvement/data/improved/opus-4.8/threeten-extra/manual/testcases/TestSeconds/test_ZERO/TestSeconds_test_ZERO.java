package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Seconds#ZERO} constant.
 */
public class TestSeconds_test_ZERO {

    @Test
    public void test_ZERO() {
        // Seconds.of(0) must return the cached ZERO singleton, not a new instance.
        assertSame(Seconds.ZERO, Seconds.of(0));
        assertEquals(Seconds.ZERO, Seconds.of(0));

        // ZERO represents exactly zero seconds.
        assertEquals(0, Seconds.ZERO.getAmount());

        // Sign checks: zero is neither negative nor positive, and is zero.
        assertFalse(Seconds.ZERO.isNegative());
        assertTrue(Seconds.ZERO.isZero());
        assertFalse(Seconds.ZERO.isPositive());
    }
}
