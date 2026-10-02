package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#multipliedBy(int)} fails fast when the
 * multiplication would underflow the {@code int} range.
 */
public class TestYears_test_multipliedBy_overflowTooSmall {

    @Test
    public void multipliedBy_throwsArithmeticException_whenResultUnderflowsIntRange() {
        // Choosing a value just below half of Integer.MIN_VALUE guarantees that
        // multiplying by 2 cannot be represented as an int, triggering overflow.
        Years tooSmallWhenDoubled = Years.of(Integer.MIN_VALUE / 2 - 1);

        assertThrows(ArithmeticException.class, () -> tooSmallWhenDoubled.multipliedBy(2));
    }
}
