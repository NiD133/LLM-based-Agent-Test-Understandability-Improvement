package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestDays_test_ONE {

    @Test
    public void test_ONE() {
        assertSame(Days.ONE, Days.of(1));
        assertEquals(Days.ONE, Days.of(1));
        assertEquals(1, Days.ONE.getAmount());
        assertFalse(Days.ONE.isNegative());
        assertFalse(Days.ONE.isZero());
        assertTrue(Days.ONE.isPositive());
    }
}
