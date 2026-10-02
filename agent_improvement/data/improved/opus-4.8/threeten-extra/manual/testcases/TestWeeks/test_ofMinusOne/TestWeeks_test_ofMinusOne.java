package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the state of a {@link Weeks} instance created from a negative amount.
 */
public class TestWeeks_test_ofMinusOne {

    @Test
    public void of_minusOne_hasNegativeAmount() {
        Weeks minusOneWeek = Weeks.of(-1);

        assertEquals(-1, minusOneWeek.getAmount(), "amount should equal the value passed to of()");
        assertTrue(minusOneWeek.isNegative(), "-1 weeks is negative");
        assertFalse(minusOneWeek.isZero(), "-1 weeks is not zero");
        assertFalse(minusOneWeek.isPositive(), "-1 weeks is not positive");
    }
}
