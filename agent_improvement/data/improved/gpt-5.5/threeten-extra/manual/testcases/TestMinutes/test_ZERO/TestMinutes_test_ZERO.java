package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_ZERO {

    @Test
    public void test_ZERO() {
        Minutes zeroMinutes = Minutes.ZERO;

        assertSame(zeroMinutes, Minutes.of(0));
        assertEquals(zeroMinutes, Minutes.of(0));
        assertEquals(0, zeroMinutes.getAmount());
        assertFalse(zeroMinutes.isNegative());
        assertTrue(zeroMinutes.isZero());
        assertFalse(zeroMinutes.isPositive());
    }
}
