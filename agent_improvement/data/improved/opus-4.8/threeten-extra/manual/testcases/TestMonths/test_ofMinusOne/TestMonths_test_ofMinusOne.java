package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#of(int)} with a negative argument produces an
 * amount whose value and sign-checking methods reflect a negative quantity.
 */
public class TestMonths_test_ofMinusOne {

    @Test
    public void of_minusOne_isNegativeOneMonth() {
        Months minusOneMonth = Months.of(-1);

        assertEquals(-1, minusOneMonth.getAmount());
        assertTrue(minusOneMonth.isNegative());
        assertFalse(minusOneMonth.isZero());
        assertFalse(minusOneMonth.isPositive());
    }
}
