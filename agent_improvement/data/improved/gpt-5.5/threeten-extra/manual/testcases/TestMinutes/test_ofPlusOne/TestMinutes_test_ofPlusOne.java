package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestMinutes_test_ofPlusOne {

    @Test
    public void test_ofPlusOne() {
        assertEquals(1, Hours.of(1).getAmount());

        Minutes oneMinute = Minutes.of(1);
        assertFalse(oneMinute.isNegative());
        assertFalse(oneMinute.isZero());
        assertTrue(oneMinute.isPositive());
    }
}
