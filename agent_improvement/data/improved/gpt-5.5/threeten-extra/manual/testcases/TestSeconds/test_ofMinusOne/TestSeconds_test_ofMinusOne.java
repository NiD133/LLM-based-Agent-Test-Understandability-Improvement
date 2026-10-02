package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofMinusOne {

    private static final int NEGATIVE_ONE_SECOND = -1;

    @Test
    public void test_ofMinusOne() {
        assertEquals(NEGATIVE_ONE_SECOND, Seconds.of(NEGATIVE_ONE_SECOND).getAmount());
        assertTrue(Seconds.of(NEGATIVE_ONE_SECOND).isNegative());
        assertFalse(Seconds.of(NEGATIVE_ONE_SECOND).isZero());
        assertFalse(Seconds.of(NEGATIVE_ONE_SECOND).isPositive());
    }
}
