package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the behaviour of the {@link Hours#ZERO} constant.
 */
public class TestHours_test_ZERO {

    @Test
    public void test_ZERO() {
        // Hours.of(0) must return the cached ZERO singleton (same reference and equal).
        assertSame(Hours.ZERO, Hours.of(0));
        assertEquals(Hours.ZERO, Hours.of(0));

        // ZERO holds exactly zero hours.
        assertEquals(0, Hours.ZERO.getAmount());

        // A zero amount is neither negative nor positive, but is zero.
        assertFalse(Hours.ZERO.isNegative());
        assertTrue(Hours.ZERO.isZero());
        assertFalse(Hours.ZERO.isPositive());
    }
}
