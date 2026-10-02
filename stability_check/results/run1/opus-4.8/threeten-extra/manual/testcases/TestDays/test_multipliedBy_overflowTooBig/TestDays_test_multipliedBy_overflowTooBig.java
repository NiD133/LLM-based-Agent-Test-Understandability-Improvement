package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#multipliedBy(int)} rejects results that
 * overflow the {@code int} range.
 */
public class TestDays_test_multipliedBy_overflowTooBig {

    @Test
    public void test_multipliedBy_overflowTooBig() {
        // (Integer.MAX_VALUE / 2 + 1) * 2 exceeds Integer.MAX_VALUE,
        // so the multiplication must fail with an ArithmeticException.
        Days justOverHalfMaxValue = Days.of(Integer.MAX_VALUE / 2 + 1);

        assertThrows(ArithmeticException.class, () -> justOverHalfMaxValue.multipliedBy(2));
    }
}
