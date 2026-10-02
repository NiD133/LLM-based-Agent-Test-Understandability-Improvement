package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#multipliedBy(int)} rejects multiplications that
 * overflow the {@code int} range instead of silently wrapping around.
 */
public class TestDays_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // Integer.MAX_VALUE / 2 + 1 is the smallest value whose product with 2
        // exceeds Integer.MAX_VALUE, so the multiplication must overflow.
        int justOverHalfOfMaxInt = Integer.MAX_VALUE / 2 + 1;
        Days days = Days.of(justOverHalfOfMaxInt);

        assertThrows(ArithmeticException.class, () -> days.multipliedBy(2));
    }
}
