package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestHours_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Hours twelveHours = Hours.of(12);

        assertDividedBy(twelveHours, 1, 12);
        assertDividedBy(twelveHours, 2, 6);
        assertDividedBy(twelveHours, 3, 4);
        assertDividedBy(twelveHours, 4, 3);
        assertDividedBy(twelveHours, 5, 2);
        assertDividedBy(twelveHours, 6, 2);
        assertDividedBy(twelveHours, -3, -4);
    }

    private static void assertDividedBy(Hours hours, int divisor, int expectedHours) {
        assertEquals(Hours.of(expectedHours), hours.dividedBy(divisor));
    }
}
