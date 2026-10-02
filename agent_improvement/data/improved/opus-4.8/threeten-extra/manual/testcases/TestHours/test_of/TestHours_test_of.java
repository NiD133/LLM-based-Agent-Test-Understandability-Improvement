package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Hours#of(int)} stores the supplied amount unchanged,
 * as later read back via {@link Hours#getAmount()}.
 */
public class TestHours_test_of {

    @Test
    public void of_returnsHoursWithGivenAmount() {
        // zero
        assertEquals(0, Hours.of(0).getAmount());

        // small positive values
        assertEquals(1, Hours.of(1).getAmount());
        assertEquals(2, Hours.of(2).getAmount());

        // small negative values
        assertEquals(-1, Hours.of(-1).getAmount());
        assertEquals(-2, Hours.of(-2).getAmount());

        // integer boundaries
        assertEquals(Integer.MAX_VALUE, Hours.of(Integer.MAX_VALUE).getAmount());
        assertEquals(Integer.MIN_VALUE, Hours.of(Integer.MIN_VALUE).getAmount());
    }
}
