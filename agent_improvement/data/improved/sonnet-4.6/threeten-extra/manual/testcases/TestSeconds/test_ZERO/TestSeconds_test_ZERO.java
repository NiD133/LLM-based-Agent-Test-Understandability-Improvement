package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ZERO {

    // Seconds.ZERO is the singleton constant representing zero seconds.
    // It must be the same instance returned by Seconds.of(0) and must
    // correctly report its sign state.
    @Test
    public void test_ZERO() {
        assertSame(Seconds.ZERO, Seconds.of(0));
        assertEquals(Seconds.ZERO, Seconds.of(0));
        assertEquals(0, Seconds.ZERO.getAmount());
        assertFalse(Seconds.ZERO.isNegative());
        assertTrue(Seconds.ZERO.isZero());
        assertFalse(Seconds.ZERO.isPositive());
    }
}
