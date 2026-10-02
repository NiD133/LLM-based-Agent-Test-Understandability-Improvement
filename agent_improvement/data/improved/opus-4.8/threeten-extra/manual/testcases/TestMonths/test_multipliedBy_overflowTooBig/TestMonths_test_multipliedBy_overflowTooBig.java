package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#multipliedBy(int)} reports arithmetic overflow.
 */
public class TestMonths_test_multipliedBy_overflowTooBig {

    @Test
    public void multipliedBy_throwsWhenResultExceedsIntMax() {
        // Choose a value whose double is just past Integer.MAX_VALUE, forcing overflow.
        int justOverHalfOfMax = Integer.MAX_VALUE / 2 + 1;
        Months months = Months.of(justOverHalfOfMax);

        assertThrows(ArithmeticException.class, () -> months.multipliedBy(2));
    }
}
