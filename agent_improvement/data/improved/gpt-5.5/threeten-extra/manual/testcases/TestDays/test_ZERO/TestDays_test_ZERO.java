package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestDays_test_ZERO {

    @Test
    public void test_ZERO() {
        assertSame(Days.ZERO, Days.of(0));
        assertEquals(Days.ZERO, Days.of(0));

        assertEquals(0, Days.ZERO.getAmount());
        assertFalse(Days.ZERO.isNegative());
        assertTrue(Days.ZERO.isZero());
        assertFalse(Days.ZERO.isPositive());
    }
}
