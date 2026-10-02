package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that subtracting a {@link java.time.temporal.TemporalAmount} from {@link Days}
 * fails with an {@link ArithmeticException} when the result overflows {@code int}.
 */
public class TestDays_test_minus_TemporalAmount_overflowTooBig {

    /**
     * Subtracting {@code Days.of(-2)} from {@code Days.of(Integer.MAX_VALUE - 1)} is
     * equivalent to adding 2, pushing the result to {@code Integer.MAX_VALUE + 1},
     * which exceeds the {@code int} range and must throw {@link ArithmeticException}.
     */
    @Test
    public void minus_temporalAmount_overflowingIntMax_throwsArithmeticException() {
        Days almostMaxDays = Days.of(Integer.MAX_VALUE - 1);
        Days negativeTwoDays = Days.of(-2);

        assertThrows(ArithmeticException.class, () -> almostMaxDays.minus(negativeTwoDays));
    }
}
