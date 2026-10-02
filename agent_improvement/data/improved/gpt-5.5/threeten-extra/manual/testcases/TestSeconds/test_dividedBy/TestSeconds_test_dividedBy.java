package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestSeconds_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Seconds twelveSeconds = Seconds.of(12);

        assertDividedBy(twelveSeconds, 1, Seconds.of(12));
        assertDividedBy(twelveSeconds, 2, Seconds.of(6));
        assertDividedBy(twelveSeconds, 3, Seconds.of(4));
        assertDividedBy(twelveSeconds, 4, Seconds.of(3));
        assertDividedBy(twelveSeconds, 5, Seconds.of(2));
        assertDividedBy(twelveSeconds, 6, Seconds.of(2));
        assertDividedBy(twelveSeconds, -3, Seconds.of(-4));
    }

    private void assertDividedBy(Seconds seconds, int divisor, Seconds expected) {
        assertEquals(expected, seconds.dividedBy(divisor));
    }
}
