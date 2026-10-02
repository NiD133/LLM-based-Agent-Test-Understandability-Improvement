package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestYears_test_ZERO {

    @Test
    public void test_ZERO() {
        assertSame(Years.ZERO, Years.of(0));
        assertEquals(Years.ZERO, Years.of(0));

        assertEquals(0, Years.ZERO.getAmount());
        assertFalse(Years.ZERO.isNegative());
        assertTrue(Years.ZERO.isZero());
        assertFalse(Years.ZERO.isPositive());
    }
}
