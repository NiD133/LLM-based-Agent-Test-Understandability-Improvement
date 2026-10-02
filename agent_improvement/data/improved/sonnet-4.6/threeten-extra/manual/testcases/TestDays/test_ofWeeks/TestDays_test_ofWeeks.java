package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_ofWeeks {

    @Test
    public void test_ofWeeks() {
        // zero and small positive week counts
        assertEquals(0, Days.ofWeeks(0).getAmount());
        assertEquals(7, Days.ofWeeks(1).getAmount());
        assertEquals(14, Days.ofWeeks(2).getAmount());

        // largest week count that does not overflow when multiplied by 7
        int maxSafeWeeks = Integer.MAX_VALUE / 7;
        assertEquals(maxSafeWeeks * 7, Days.ofWeeks(maxSafeWeeks).getAmount());

        // negative week counts
        assertEquals(-7, Days.ofWeeks(-1).getAmount());
        assertEquals(-14, Days.ofWeeks(-2).getAmount());

        // most-negative week count that does not overflow when multiplied by 7
        int minSafeWeeks = Integer.MIN_VALUE / 7;
        assertEquals(minSafeWeeks * 7, Days.ofWeeks(minSafeWeeks).getAmount());
    }
}
