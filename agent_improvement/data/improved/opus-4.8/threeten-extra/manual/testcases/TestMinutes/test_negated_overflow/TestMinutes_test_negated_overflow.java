package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Minutes#negated()} reports overflow rather than wrapping around.
 */
public class TestMinutes_test_negated_overflow {

    /**
     * Negating {@code Integer.MIN_VALUE} minutes has no representable positive
     * counterpart, so {@code negated()} must throw an {@link ArithmeticException}.
     */
    @Test
    public void test_negated_overflow() {
        Minutes mostNegative = Minutes.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> mostNegative.negated());
    }
}
