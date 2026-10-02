package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the {@link Days#ONE} constant, which represents an amount of exactly one day.
 */
public class TestDays_test_ONE {

    @Test
    public void test_ONE() {
        // Days.ONE must be the cached singleton returned by Days.of(1)...
        assertSame(Days.ONE, Days.of(1));
        // ...and therefore equal to it.
        assertEquals(Days.ONE, Days.of(1));

        // It holds an amount of one day.
        assertEquals(1, Days.ONE.getAmount());

        // One day is strictly positive: not negative, not zero, positive.
        assertFalse(Days.ONE.isNegative());
        assertFalse(Days.ONE.isZero());
        assertTrue(Days.ONE.isPositive());
    }
}
