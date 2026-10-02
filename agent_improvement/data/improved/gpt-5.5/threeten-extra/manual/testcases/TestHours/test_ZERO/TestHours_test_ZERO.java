package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestHours_test_ZERO {

    @Test
    public void test_ZERO() {
        assertSame(Hours.ZERO, Hours.of(0));
        assertEquals(Hours.ZERO, Hours.of(0));

        assertEquals(0, Hours.ZERO.getAmount());
        assertFalse(Hours.ZERO.isNegative());
        assertTrue(Hours.ZERO.isZero());
        assertFalse(Hours.ZERO.isPositive());
    }
}
