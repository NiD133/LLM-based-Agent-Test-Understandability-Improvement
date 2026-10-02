package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#plus(int)} reports integer underflow.
 */
public class TestSeconds_test_plus_int_overflowTooSmall {

    /**
     * Adding -2 to (Integer.MIN_VALUE + 1) underflows below Integer.MIN_VALUE,
     * so the addition must fail with an {@link ArithmeticException}.
     */
    @Test
    public void test_plus_int_overflowTooSmall() {
        Seconds almostMinSeconds = Seconds.of(Integer.MIN_VALUE + 1);

        assertThrows(ArithmeticException.class, () -> almostMinSeconds.plus(-2));
    }
}
