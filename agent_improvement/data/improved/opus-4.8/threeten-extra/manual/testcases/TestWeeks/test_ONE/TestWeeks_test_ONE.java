package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the {@link Weeks#ONE} constant: the predefined amount representing exactly one week.
 */
public class TestWeeks_test_ONE {

    @Test
    public void test_ONE() {
        // Weeks.of(1) must return the cached ONE singleton, not a new instance.
        assertSame(Weeks.ONE, Weeks.of(1), "Weeks.of(1) should return the ONE singleton");
        assertEquals(Weeks.ONE, Weeks.of(1), "Weeks.ONE should equal Weeks.of(1)");

        // ONE represents an amount of exactly one week.
        assertEquals(1, Weeks.ONE.getAmount(), "ONE should hold an amount of 1 week");

        // One week is strictly positive: not negative and not zero.
        assertFalse(Weeks.ONE.isNegative(), "ONE should not be negative");
        assertFalse(Weeks.ONE.isZero(), "ONE should not be zero");
        assertTrue(Weeks.ONE.isPositive(), "ONE should be positive");
    }
}
