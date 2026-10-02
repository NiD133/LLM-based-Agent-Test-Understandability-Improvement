package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that negating {@link Seconds} guards against integer overflow.
 */
public class TestSeconds_test_negated_overflow {

    /**
     * Negating {@code Integer.MIN_VALUE} seconds has no representable positive
     * counterpart, so {@code negated()} must fail with an {@link ArithmeticException}.
     */
    @Test
    public void negating_minValue_throwsArithmeticException() {
        Seconds minValueSeconds = Seconds.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> minValueSeconds.negated());
    }
}
