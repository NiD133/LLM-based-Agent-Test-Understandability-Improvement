package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestWeeks_test_ZERO {

    @Test
    @DisplayName("Weeks.ZERO is the singleton for zero weeks with correct state flags")
    public void test_ZERO() {
        assertSame(Weeks.ZERO, Weeks.of(0), "Weeks.of(0) must return the ZERO singleton");
        assertEquals(Weeks.ZERO, Weeks.of(0), "Weeks.ZERO must equal Weeks.of(0)");
        assertEquals(0, Weeks.ZERO.getAmount(), "ZERO amount must be 0");
        assertFalse(Weeks.ZERO.isNegative(), "ZERO must not be negative");
        assertTrue(Weeks.ZERO.isZero(), "ZERO must report isZero() as true");
        assertFalse(Weeks.ZERO.isPositive(), "ZERO must not be positive");
    }
}
