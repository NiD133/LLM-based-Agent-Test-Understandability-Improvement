package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#of(int)} when created with the value -1.
 */
public class TestYears_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        Years minusOne = Years.of(-1);

        assertEquals(-1, minusOne.getAmount(), "getAmount() should return the value passed to of()");
        assertTrue(minusOne.isNegative(), "-1 years should be negative");
        assertFalse(minusOne.isZero(), "-1 years should not be zero");
        assertFalse(minusOne.isPositive(), "-1 years should not be positive");
    }
}
