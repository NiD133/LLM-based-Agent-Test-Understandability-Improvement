package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Days#ofWeeks(int)} reports numeric overflow.
 */
public class TestDays_test_ofWeeks_overflow {

    /**
     * {@code ofWeeks} multiplies the week count by 7 (days per week).
     * A week count just above {@code Integer.MAX_VALUE / 7} makes that
     * multiplication exceed the range of an {@code int}, so the factory
     * must fail fast with an {@link ArithmeticException}.
     */
    @Test
    public void test_ofWeeks_overflow() {
        int weeksThatOverflowWhenConvertedToDays = (Integer.MAX_VALUE / 7) + 7;

        assertThrows(ArithmeticException.class,
                () -> Days.ofWeeks(weeksThatOverflowWhenConvertedToDays));
    }
}
