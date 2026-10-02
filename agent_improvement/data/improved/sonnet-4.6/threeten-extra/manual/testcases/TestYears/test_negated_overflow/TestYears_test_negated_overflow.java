package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that negating {@code Years.of(Integer.MIN_VALUE)} throws ArithmeticException,
 * because its mathematical negation (Integer.MAX_VALUE + 1) exceeds the int range.
 */
public class TestYears_test_negated_overflow {

    @Test
    public void test_negated_overflow() {
        // Integer.MIN_VALUE cannot be negated within int range — expect overflow
        assertThrows(ArithmeticException.class, () -> Years.of(Integer.MIN_VALUE).negated());
    }
}
