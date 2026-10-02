package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#abs()} reports arithmetic overflow.
 */
public class TestDays_test_abs_overflow {

    /**
     * The absolute value of {@code Integer.MIN_VALUE} cannot be represented as an
     * {@code int}, so calling {@code abs()} on it must throw an exception.
     */
    @Test
    public void abs_throwsWhenResultOverflows() {
        Days mostNegativeDays = Days.of(Integer.MIN_VALUE);

        assertThrows(ArithmeticException.class, () -> mostNegativeDays.abs());
    }
}
