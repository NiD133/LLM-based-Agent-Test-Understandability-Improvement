package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestMonths_test_ONE {

    /**
     * Verifies that Months.ONE is the canonical singleton for 1 month:
     * same instance as Months.of(1), carries the value 1, and reports
     * itself as positive (not zero, not negative).
     */
    @Test
    public void test_ONE() {
        assertSame(Months.ONE, Months.of(1));
        assertEquals(Months.ONE, Months.of(1));
        assertEquals(1, Months.ONE.getAmount());
        assertFalse(Months.ONE.isNegative());
        assertFalse(Months.ONE.isZero());
        assertTrue(Months.ONE.isPositive());
    }
}
