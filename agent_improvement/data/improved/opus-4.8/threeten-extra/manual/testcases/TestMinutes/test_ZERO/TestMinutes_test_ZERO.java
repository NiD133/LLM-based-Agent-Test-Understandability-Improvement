package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Minutes#ZERO} constant.
 */
public class TestMinutes_test_ZERO {

    @Test
    public void test_ZERO() {
        // Minutes.of(0) must return the cached ZERO singleton (same reference and equal).
        assertSame(Minutes.ZERO, Minutes.of(0));
        assertEquals(Minutes.ZERO, Minutes.of(0));

        // ZERO represents exactly zero minutes.
        assertEquals(0, Minutes.ZERO.getAmount());

        // Sign checks for a zero amount.
        assertFalse(Minutes.ZERO.isNegative());
        assertTrue(Minutes.ZERO.isZero());
        assertFalse(Minutes.ZERO.isPositive());
    }
}
