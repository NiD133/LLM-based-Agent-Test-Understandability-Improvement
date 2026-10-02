package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#dividedBy(int)} performs signed integer division,
 * yielding a negative result when a positive amount is divided by a negative divisor.
 */
public class TestDays_test_dividedBy_negate {

    @Test
    public void test_dividedBy_negate() {
        Days twelveDays = Days.of(12);

        // 12 / -3 = -4, using integer division
        Days result = twelveDays.dividedBy(-3);

        assertEquals(Days.of(-4), result);
    }
}
