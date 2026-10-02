package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#plus(java.time.temporal.TemporalAmount)} reports
 * an arithmetic overflow when the sum of two amounts is smaller than what an
 * {@code int} can represent.
 */
public class TestWeeks_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void plus_belowIntMinValue_throwsArithmeticException() {
        // The smallest representable result is Integer.MIN_VALUE. Starting just
        // above it (MIN_VALUE + 1) and adding -2 underflows past MIN_VALUE.
        Weeks nearMinimum = Weeks.of(Integer.MIN_VALUE + 1);
        Weeks amountToAdd = Weeks.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(amountToAdd));
    }
}
