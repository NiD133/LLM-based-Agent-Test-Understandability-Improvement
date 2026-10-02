package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests the {@link Months#ONE} constant.
 */
public class TestMonths_test_ONE {

    @Test
    public void test_ONE() {
        // ONE is the cached singleton returned by of(1).
        assertSame(Months.ONE, Months.of(1));
        assertEquals(Months.ONE, Months.of(1));

        // ONE represents exactly one (positive) month.
        assertEquals(1, Months.ONE.getAmount());
        assertFalse(Months.ONE.isNegative());
        assertFalse(Months.ONE.isZero());
        assertTrue(Months.ONE.isPositive());
    }
}
