package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Years#plus(java.time.temporal.TemporalAmount)} fails with an
 * {@link ArithmeticException} when the addition underflows the {@code int} range.
 */
public class TestYears_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void plus_underflowsIntMinimum_throwsArithmeticException() {
        // Smallest representable amount; adding -2 years pushes the total below Integer.MIN_VALUE.
        Years nearMinimum = Years.of(Integer.MIN_VALUE + 1);
        Years amountToAdd = Years.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(amountToAdd));
    }
}
