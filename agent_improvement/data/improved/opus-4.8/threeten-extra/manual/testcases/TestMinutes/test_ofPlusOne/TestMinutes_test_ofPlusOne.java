package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the sign-related state of a single positive amount of time.
 */
public class TestMinutes_test_ofPlusOne {

    @Test
    public void test_ofPlusOne() {
        // One hour reports an amount of one.
        assertEquals(1, Hours.of(1).getAmount());

        // One minute is strictly positive, so it is neither negative nor zero.
        Minutes oneMinute = Minutes.of(1);
        assertFalse(oneMinute.isNegative(), "1 minute should not be negative");
        assertFalse(oneMinute.isZero(), "1 minute should not be zero");
        assertTrue(oneMinute.isPositive(), "1 minute should be positive");
    }
}
