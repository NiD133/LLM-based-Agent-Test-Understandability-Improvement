package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#multipliedBy(int)} rejects multiplications whose
 * result would underflow below {@link Integer#MIN_VALUE}.
 */
public class TestSeconds_test_multipliedBy_overflowTooSmall {

    @Test
    public void test_multipliedBy_overflowTooSmall() {
        // (Integer.MIN_VALUE / 2 - 1) * 2 underflows the int range, so the multiplication must fail.
        Seconds nearMinimum = Seconds.of(Integer.MIN_VALUE / 2 - 1);

        assertThrows(ArithmeticException.class, () -> nearMinimum.multipliedBy(2));
    }
}
