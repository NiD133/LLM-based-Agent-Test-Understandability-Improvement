package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestHours_test_ofPlusOne {

    @Test
    public void of_oneHourCreatesPositiveNonZeroAmount() {
        assertEquals(1, Hours.of(1).getAmount());
        assertFalse(Hours.of(1).isNegative());
        assertFalse(Hours.of(1).isZero());
        assertTrue(Hours.of(1).isPositive());
    }
}
