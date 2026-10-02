package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        assertEquals(-1, Hours.of(-1).getAmount());

        Minutes minusOne = Minutes.of(-1);
        assertTrue(minusOne.isNegative());
        assertFalse(minusOne.isZero());
        assertFalse(minusOne.isPositive());
    }
}
