package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestDays_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        assertEquals(-1, Days.of(-1).getAmount());
        assertTrue(Days.of(-1).isNegative());
        assertFalse(Days.of(-1).isZero());
        assertFalse(Days.of(-1).isPositive());
    }
}
