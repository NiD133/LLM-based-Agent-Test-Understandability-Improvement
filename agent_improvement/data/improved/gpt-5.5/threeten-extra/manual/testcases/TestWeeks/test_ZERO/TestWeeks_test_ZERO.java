package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_ZERO {

    @Test
    public void test_ZERO() {
        assertSame(Weeks.ZERO, Weeks.of(0));
        assertEquals(Weeks.ZERO, Weeks.of(0));

        assertEquals(0, Weeks.ZERO.getAmount());

        assertFalse(Weeks.ZERO.isNegative());
        assertTrue(Weeks.ZERO.isZero());
        assertFalse(Weeks.ZERO.isPositive());
    }
}
