package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestMonths_test_ofMinusOne {

    private static final int ONE_MONTH_NEGATIVE = -1;

    @Test
    public void test_ofMinusOne() {
        assertEquals(ONE_MONTH_NEGATIVE, Months.of(ONE_MONTH_NEGATIVE).getAmount());
        assertTrue(Months.of(ONE_MONTH_NEGATIVE).isNegative());
        assertFalse(Months.of(ONE_MONTH_NEGATIVE).isZero());
        assertFalse(Months.of(ONE_MONTH_NEGATIVE).isPositive());
    }
}
