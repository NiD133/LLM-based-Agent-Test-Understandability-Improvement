package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Days#ONE} constant, which represents exactly one day.
 * <p>
 * Verifies that {@code Days.ONE} is the cached singleton returned by
 * {@code Days.of(1)}, that its numeric amount is 1, and that the sign-query
 * methods ({@code isNegative}, {@code isZero}, {@code isPositive}) all
 * return the correct values for a positive unit amount.
 */
public class TestDays_test_ONE {

    @Test
    public void test_ONE() {
        // Days.ONE must be the exact same instance that Days.of(1) returns (singleton cache).
        assertSame(Days.ONE, Days.of(1));

        // Content equality: Days.ONE equals Days.of(1) by value as well as identity.
        assertEquals(Days.ONE, Days.of(1));

        // The underlying day count must be exactly 1.
        assertEquals(1, Days.ONE.getAmount());

        // ONE represents a positive amount, so it must not be reported as negative or zero.
        assertFalse(Days.ONE.isNegative());
        assertFalse(Days.ONE.isZero());
        assertTrue(Days.ONE.isPositive());
    }
}
