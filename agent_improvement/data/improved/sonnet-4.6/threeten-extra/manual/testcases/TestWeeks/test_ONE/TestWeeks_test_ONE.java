package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Weeks.ONE constant")
public class TestWeeks_test_ONE {

    @Test
    @DisplayName("ONE is the same singleton instance as Weeks.of(1)")
    public void test_ONE_isSingletonIdentity() {
        assertSame(Weeks.ONE, Weeks.of(1));
    }

    @Test
    @DisplayName("ONE is equal in value to Weeks.of(1)")
    public void test_ONE_equalsWeeksOfOne() {
        assertEquals(Weeks.ONE, Weeks.of(1));
    }

    @Test
    @DisplayName("ONE has an amount of 1")
    public void test_ONE_getAmountReturnsOne() {
        assertEquals(1, Weeks.ONE.getAmount());
    }

    @Test
    @DisplayName("ONE is not negative")
    public void test_ONE_isNotNegative() {
        assertFalse(Weeks.ONE.isNegative());
    }

    @Test
    @DisplayName("ONE is not zero")
    public void test_ONE_isNotZero() {
        assertFalse(Weeks.ONE.isZero());
    }

    @Test
    @DisplayName("ONE is positive")
    public void test_ONE_isPositive() {
        assertTrue(Weeks.ONE.isPositive());
    }
}
