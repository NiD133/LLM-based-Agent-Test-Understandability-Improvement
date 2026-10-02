package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestYears_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        int expectedAmount = -1;

        assertEquals(expectedAmount, Years.of(-1).getAmount());
        assertTrue(Years.of(-1).isNegative());
        assertFalse(Years.of(-1).isZero());
        assertFalse(Years.of(-1).isPositive());
    }
}
