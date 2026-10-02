package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestDays_test_ZERO {

    @Test
    @DisplayName("Days.ZERO is the singleton for zero days with correct sign and zero-state")
    public void test_ZERO() {
        // Days.of(0) must return the canonical ZERO singleton, not a new instance
        assertSame(Days.ZERO, Days.of(0));
        assertEquals(Days.ZERO, Days.of(0));

        // ZERO must expose an amount of 0 and report neither negative nor positive
        assertEquals(0, Days.ZERO.getAmount());
        assertFalse(Days.ZERO.isNegative());
        assertTrue(Days.ZERO.isZero());
        assertFalse(Days.ZERO.isPositive());
    }
}
