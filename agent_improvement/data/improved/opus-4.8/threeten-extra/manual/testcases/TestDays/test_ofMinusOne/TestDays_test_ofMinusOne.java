package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the sign-related queries on a {@link Days} amount of minus one day.
 */
public class TestDays_test_ofMinusOne {

    @Test
    public void minusOneDay_reportsNegativeAmount() {
        Days minusOneDay = Days.of(-1);

        assertEquals(-1, minusOneDay.getAmount(), "amount should be -1");
        assertTrue(minusOneDay.isNegative(), "-1 day is negative");
        assertFalse(minusOneDay.isZero(), "-1 day is not zero");
        assertFalse(minusOneDay.isPositive(), "-1 day is not positive");
    }
}
