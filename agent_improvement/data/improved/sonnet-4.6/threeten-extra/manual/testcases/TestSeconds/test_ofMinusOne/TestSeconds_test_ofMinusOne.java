package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        Seconds minusOne = Seconds.of(-1);

        assertEquals(-1, minusOne.getAmount());
        assertTrue(minusOne.isNegative());
        assertFalse(minusOne.isZero());
        assertFalse(minusOne.isPositive());
    }
}
