package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#minus(int)} reports integer overflow.
 */
public class TestWeeks_test_minus_int_overflowTooBig {

    @Test
    public void minus_whenResultOverflowsIntMax_throwsArithmeticException() {
        // Subtracting -2 effectively adds 2, pushing (MAX_VALUE - 1) past Integer.MAX_VALUE.
        Weeks nearMax = Weeks.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> nearMax.minus(-2));
    }
}
