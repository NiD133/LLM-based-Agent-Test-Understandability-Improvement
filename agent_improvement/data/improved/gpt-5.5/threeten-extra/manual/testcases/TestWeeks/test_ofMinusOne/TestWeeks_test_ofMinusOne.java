package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        Weeks minusOneWeek = Weeks.of(-1);

        assertEquals(-1, minusOneWeek.getAmount());
        assertTrue(minusOneWeek.isNegative());
        assertFalse(minusOneWeek.isZero());
        assertFalse(minusOneWeek.isPositive());
    }
}
