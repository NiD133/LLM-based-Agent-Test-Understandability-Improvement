package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the state of a {@link Seconds} instance created from a negative amount.
 */
public class TestSeconds_test_ofMinusOne {

    @Test
    public void of_negativeOne_reportsNegativeState() {
        Seconds minusOneSecond = Seconds.of(-1);

        assertEquals(-1, minusOneSecond.getAmount(), "amount should equal the value passed to of()");
        assertTrue(minusOneSecond.isNegative(), "-1 second is negative");
        assertFalse(minusOneSecond.isZero(), "-1 second is not zero");
        assertFalse(minusOneSecond.isPositive(), "-1 second is not positive");
    }
}
