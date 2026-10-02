package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_ofMinusOne {

    private static final int NEGATIVE_ONE = -1;

    @Test
    public void test_ofMinusOne() {
        assertEquals(NEGATIVE_ONE, Hours.of(NEGATIVE_ONE).getAmount());
        assertTrue(Minutes.of(NEGATIVE_ONE).isNegative());
        assertFalse(Minutes.of(NEGATIVE_ONE).isZero());
        assertFalse(Minutes.of(NEGATIVE_ONE).isPositive());
    }
}
