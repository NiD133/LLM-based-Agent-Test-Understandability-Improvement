package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TestHours_test_ofPlusOne {

    @Test
    public void test_ofPlusOne() {
        // Hours.of(1) represents a positive, non-zero duration of one hour
        Hours oneHour = Hours.of(1);
        assertEquals(1, oneHour.getAmount());
        assertFalse(oneHour.isNegative());
        assertFalse(oneHour.isZero());
        assertTrue(oneHour.isPositive());
    }
}
