package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the sign-related queries for a negative {@link Minutes} amount,
 * using minus one (-1) as the representative negative value.
 */
public class TestMinutes_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        // A negative hour amount keeps its raw value: Hours.of(-1) holds -1.
        assertEquals(-1, Hours.of(-1).getAmount());

        // Minutes.of(-1) is a negative amount, so only isNegative() should be true.
        Minutes minusOneMinute = Minutes.of(-1);
        assertTrue(minusOneMinute.isNegative(), "-1 minute is negative");
        assertFalse(minusOneMinute.isZero(), "-1 minute is not zero");
        assertFalse(minusOneMinute.isPositive(), "-1 minute is not positive");
    }
}
