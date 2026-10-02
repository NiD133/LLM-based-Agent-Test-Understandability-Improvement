package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Months#ZERO} constant.
 */
public class TestMonths_test_ZERO {

    @Test
    public void test_ZERO() {
        // Months.of(0) must return the cached ZERO singleton (same identity, and equal).
        assertSame(Months.ZERO, Months.of(0), "Months.of(0) should return the ZERO singleton");
        assertEquals(Months.ZERO, Months.of(0), "Months.of(0) should equal ZERO");

        // The ZERO constant represents an amount of exactly zero months.
        assertEquals(0, Months.ZERO.getAmount(), "ZERO should hold an amount of 0 months");

        // Zero is neither negative nor positive; only isZero() is true.
        assertFalse(Months.ZERO.isNegative(), "ZERO should not be negative");
        assertTrue(Months.ZERO.isZero(), "ZERO should report itself as zero");
        assertFalse(Months.ZERO.isPositive(), "ZERO should not be positive");
    }
}
