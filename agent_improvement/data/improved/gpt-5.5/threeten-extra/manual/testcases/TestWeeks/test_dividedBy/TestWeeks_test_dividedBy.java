package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestWeeks_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Weeks twelveWeeks = Weeks.of(12);

        assertDividedBy(twelveWeeks, 1, 12);
        assertDividedBy(twelveWeeks, 2, 6);
        assertDividedBy(twelveWeeks, 3, 4);
        assertDividedBy(twelveWeeks, 4, 3);
        assertDividedBy(twelveWeeks, 5, 2);
        assertDividedBy(twelveWeeks, 6, 2);
        assertDividedBy(twelveWeeks, -3, -4);
    }

    private void assertDividedBy(Weeks weeks, int divisor, int expectedWeeks) {
        assertEquals(Weeks.of(expectedWeeks), weeks.dividedBy(divisor));
    }
}
