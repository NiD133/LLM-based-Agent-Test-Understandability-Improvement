package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#multipliedBy(int)}.
 */
public class TestDays_test_multipliedBy {

    /**
     * Verifies that multiplying a {@code Days} amount by a scalar scales the
     * number of days accordingly, covering zero, the identity factor,
     * positive factors and a negative factor.
     */
    @Test
    public void test_multipliedBy() {
        Days fiveDays = Days.of(5);

        assertEquals(Days.of(0), fiveDays.multipliedBy(0), "5 days x 0 should be 0 days");
        assertEquals(Days.of(5), fiveDays.multipliedBy(1), "5 days x 1 should be unchanged");
        assertEquals(Days.of(10), fiveDays.multipliedBy(2), "5 days x 2 should be 10 days");
        assertEquals(Days.of(15), fiveDays.multipliedBy(3), "5 days x 3 should be 15 days");
        assertEquals(Days.of(-15), fiveDays.multipliedBy(-3), "5 days x -3 should be -15 days");
    }
}
