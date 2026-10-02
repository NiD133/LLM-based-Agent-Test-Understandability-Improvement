package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#plus(java.time.temporal.TemporalAmount)} reports
 * integer underflow as an {@link ArithmeticException}.
 */
public class TestSeconds_test_plus_TemporalAmount_overflowTooSmall {

    @Test
    public void plus_whenResultUnderflowsIntMin_throwsArithmeticException() {
        // One above Integer.MIN_VALUE; adding -2 would drop below Integer.MIN_VALUE.
        Seconds nearMinimum = Seconds.of(Integer.MIN_VALUE + 1);
        Seconds amountToAdd = Seconds.of(-2);

        assertThrows(ArithmeticException.class, () -> nearMinimum.plus(amountToAdd));
    }
}
