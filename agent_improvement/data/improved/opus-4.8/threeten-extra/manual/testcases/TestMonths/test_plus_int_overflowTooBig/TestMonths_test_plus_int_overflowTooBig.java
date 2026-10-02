package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#plus(int)} rejects additions that overflow an int.
 */
public class TestMonths_test_plus_int_overflowTooBig {

    @Test
    public void plus_int_throwsWhenResultOverflowsIntMax() {
        // Adding 2 to (Integer.MAX_VALUE - 1) exceeds Integer.MAX_VALUE.
        Months almostMaxMonths = Months.of(Integer.MAX_VALUE - 1);

        assertThrows(ArithmeticException.class, () -> almostMaxMonths.plus(2));
    }
}
