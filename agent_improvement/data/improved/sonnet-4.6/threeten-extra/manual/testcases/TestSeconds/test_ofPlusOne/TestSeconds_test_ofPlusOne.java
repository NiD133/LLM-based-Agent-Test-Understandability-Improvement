package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_ofPlusOne {

    @Test
    public void test_ofPlusOne() {
        Seconds oneSecond = Seconds.of(1);
        assertEquals(1, oneSecond.getAmount());
        assertFalse(oneSecond.isNegative());
        assertFalse(oneSecond.isZero());
        assertTrue(oneSecond.isPositive());
    }
}
