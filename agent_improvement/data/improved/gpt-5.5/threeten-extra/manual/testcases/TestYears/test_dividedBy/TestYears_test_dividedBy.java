package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestYears_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Years twelveYears = Years.of(12);

        assertDividedBy(twelveYears, 1, 12);
        assertDividedBy(twelveYears, 2, 6);
        assertDividedBy(twelveYears, 3, 4);
        assertDividedBy(twelveYears, 4, 3);
        assertDividedBy(twelveYears, 5, 2);
        assertDividedBy(twelveYears, 6, 2);
        assertDividedBy(twelveYears, -3, -4);
    }

    private static void assertDividedBy(Years baseYears, int divisor, int expectedYears) {
        assertEquals(Years.of(expectedYears), baseYears.dividedBy(divisor));
    }
}
