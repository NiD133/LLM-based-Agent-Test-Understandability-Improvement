package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ZERO {

    @Test
    public void test_ZERO() {
        Seconds zeroFromFactoryForIdentity = Seconds.of(0);
        Seconds zeroFromFactoryForEquality = Seconds.of(0);

        assertSame(Seconds.ZERO, zeroFromFactoryForIdentity);
        assertEquals(Seconds.ZERO, zeroFromFactoryForEquality);
        assertEquals(0, Seconds.ZERO.getAmount());
        assertFalse(Seconds.ZERO.isNegative());
        assertTrue(Seconds.ZERO.isZero());
        assertFalse(Seconds.ZERO.isPositive());
    }
}
