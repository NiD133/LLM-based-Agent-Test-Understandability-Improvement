package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestHours_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Hours twelveHours = Hours.of(12);

        // Dividing by 1 always returns the same value
        assertEquals(Hours.of(12), twelveHours.dividedBy(1));

        // Exact integer division
        assertEquals(Hours.of(6), twelveHours.dividedBy(2));
        assertEquals(Hours.of(4), twelveHours.dividedBy(3));
        assertEquals(Hours.of(3), twelveHours.dividedBy(4));
        assertEquals(Hours.of(2), twelveHours.dividedBy(6));

        // Fractional results are truncated toward zero: 12 / 5 = 2 (not 2.4)
        assertEquals(Hours.of(2), twelveHours.dividedBy(5));

        // Negative divisor negates the result: 12 / -3 = -4
        assertEquals(Hours.of(-4), twelveHours.dividedBy(-3));
    }
}
