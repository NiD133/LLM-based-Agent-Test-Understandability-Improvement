package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestMonths_test_ZERO {

    @Test
    public void test_ZERO() {
        assertSame(Months.ZERO, Months.of(0));
        assertEquals(Months.ZERO, Months.of(0));
        assertEquals(0, Months.ZERO.getAmount());
        assertFalse(Months.ZERO.isNegative());
        assertTrue(Months.ZERO.isZero());
        assertFalse(Months.ZERO.isPositive());
    }
}
