package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_ofWeeks {

    @Test
    public void test_ofWeeks() {
        assertDaysInWeeks(0, 0);
        assertDaysInWeeks(1, 7);
        assertDaysInWeeks(2, 14);
        assertDaysInWeeks(Integer.MAX_VALUE / 7, (Integer.MAX_VALUE / 7) * 7);
        assertDaysInWeeks(-1, -7);
        assertDaysInWeeks(-2, -14);
        assertDaysInWeeks(Integer.MIN_VALUE / 7, (Integer.MIN_VALUE / 7) * 7);
    }

    private static void assertDaysInWeeks(int weeks, int expectedDays) {
        assertEquals(expectedDays, Days.ofWeeks(weeks).getAmount());
    }
}
