package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Years#ZERO} constant.
 */
public class TestYears_test_ZERO {

    @Test
    public void test_ZERO_isTheCanonicalZeroInstance() {
        // Years.of(0) must return the shared ZERO singleton, not just an equal value.
        assertSame(Years.ZERO, Years.of(0));
        assertEquals(Years.ZERO, Years.of(0));
    }

    @Test
    public void test_ZERO_hasExpectedState() {
        assertEquals(0, Years.ZERO.getAmount());
        assertFalse(Years.ZERO.isNegative());
        assertTrue(Years.ZERO.isZero());
        assertFalse(Years.ZERO.isPositive());
    }
}
