package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestHours_test_ofMinusOne {

    @Test
    public void test_ofMinusOne() {
        assertEquals(-1, Hours.of(-1).getAmount());
        assertTrue(Hours.of(-1).isNegative());
        assertFalse(Hours.of(-1).isZero());
        assertFalse(Hours.of(-1).isPositive());
    }
}
