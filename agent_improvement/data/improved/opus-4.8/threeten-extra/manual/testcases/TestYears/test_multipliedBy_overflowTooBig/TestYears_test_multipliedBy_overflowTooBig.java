package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Years#multipliedBy(int)} throws when the multiplication
 * overflows the {@code int} range.
 */
public class TestYears_test_multipliedBy_overflowTooBig {

    @Test
    public void multipliedBy_overflowsWhenResultExceedsIntMax() {
        // Multiplying a value just above half of Integer.MAX_VALUE by 2 overflows an int.
        Years tooLargeToDouble = Years.of(Integer.MAX_VALUE / 2 + 1);

        assertThrows(ArithmeticException.class, () -> tooLargeToDouble.multipliedBy(2));
    }
}
