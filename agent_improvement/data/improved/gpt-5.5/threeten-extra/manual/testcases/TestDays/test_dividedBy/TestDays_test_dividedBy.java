package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDays_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Days twelveDays = Days.of(12);

        assertDividedBy(twelveDays, 1, 12);
        assertDividedBy(twelveDays, 2, 6);
        assertDividedBy(twelveDays, 3, 4);
        assertDividedBy(twelveDays, 4, 3);
        assertDividedBy(twelveDays, 5, 2);
        assertDividedBy(twelveDays, 6, 2);
        assertDividedBy(twelveDays, -3, -4);
    }

    private static void assertDividedBy(Days amount, int divisor, int expectedDays) {
        assertEquals(Days.of(expectedDays), amount.dividedBy(divisor));
    }
}
