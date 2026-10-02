package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TestDays_test_minus_TemporalAmount_overflowTooSmall {

    // One step above the minimum so subtracting 2 pushes below Integer.MIN_VALUE.
    private static final int NEAR_MIN = Integer.MIN_VALUE + 1;
    private static final int AMOUNT_CAUSING_UNDERFLOW = 2;

    @Test
    public void test_minus_TemporalAmount_overflowTooSmall() {
        Days nearMinimum = Days.of(NEAR_MIN);
        Days amountToSubtract = Days.of(AMOUNT_CAUSING_UNDERFLOW);

        assertThrows(ArithmeticException.class, () -> nearMinimum.minus(amountToSubtract));
    }
}
