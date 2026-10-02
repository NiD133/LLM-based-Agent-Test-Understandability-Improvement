package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the state-inspection methods of {@link Hours} for a negative amount.
 */
public class TestHours_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        Hours minusOneHour = Hours.of(-1);

        assertEquals(-1, minusOneHour.getAmount(), "getAmount() should return the stored value");
        assertTrue(minusOneHour.isNegative(), "-1 hours is negative");
        assertFalse(minusOneHour.isZero(), "-1 hours is not zero");
        assertFalse(minusOneHour.isPositive(), "-1 hours is not positive");
    }
}
