package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the {@link Days#ZERO} constant and the state it exposes.
 */
public class TestDays_test_ZERO {

    @Test
    public void test_ZERO() {
        // Days.of(0) is expected to return the cached ZERO singleton, not a new instance.
        assertSame(Days.ZERO, Days.of(0));
        assertEquals(Days.ZERO, Days.of(0));

        // ZERO represents exactly zero days.
        assertEquals(0, Days.ZERO.getAmount());

        // Sign checks: zero is neither negative nor positive, and is reported as zero.
        assertFalse(Days.ZERO.isNegative());
        assertTrue(Days.ZERO.isZero());
        assertFalse(Days.ZERO.isPositive());
    }
}
