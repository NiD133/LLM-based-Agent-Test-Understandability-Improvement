package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestYears_test_ONE {

    @Test
    public void test_ONE() {
        // Years.ONE is a cached singleton: of(1) must return the exact same instance
        assertSame(Years.ONE, Years.of(1));
        assertEquals(Years.ONE, Years.of(1));

        // The underlying amount must be exactly 1
        assertEquals(1, Years.ONE.getAmount());

        // ONE is strictly positive: not negative and not zero
        assertFalse(Years.ONE.isNegative());
        assertFalse(Years.ONE.isZero());
        assertTrue(Years.ONE.isPositive());
    }
}
