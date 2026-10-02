package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that negating {@code Days.of(Integer.MIN_VALUE)} throws ArithmeticException.
 * In two's-complement arithmetic, -Integer.MIN_VALUE cannot be represented as an int,
 * so {@code Days.negated()} must detect and reject this overflow.
 */
public class TestDays_test_negated_overflow {

    @Test
    public void test_negated_overflow() {
        // Integer.MIN_VALUE has no positive counterpart in int range, so negation overflows
        Days minDays = Days.of(Integer.MIN_VALUE);
        assertThrows(ArithmeticException.class, minDays::negated);
    }
}
