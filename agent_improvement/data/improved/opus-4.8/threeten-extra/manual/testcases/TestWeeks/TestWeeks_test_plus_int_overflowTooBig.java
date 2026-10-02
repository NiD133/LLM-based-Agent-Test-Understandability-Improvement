package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#plus(int)} reports integer overflow.
 */
public class TestWeeks_test_plus_int_overflowTooBig {

    @Test
    public void plus_whenResultExceedsIntegerMaxValue_throwsArithmeticException() {
        Weeks almostMaxWeeks = Weeks.of(Integer.MAX_VALUE - 1);

        // Adding 2 pushes the total past Integer.MAX_VALUE, which must overflow.
        assertThrows(ArithmeticException.class, () -> almostMaxWeeks.plus(2));
    }
}
