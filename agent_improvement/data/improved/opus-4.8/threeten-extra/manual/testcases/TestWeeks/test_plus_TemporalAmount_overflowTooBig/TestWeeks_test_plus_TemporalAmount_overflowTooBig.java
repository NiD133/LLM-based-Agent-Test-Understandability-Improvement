package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that adding weeks via {@link Weeks#plus(java.time.temporal.TemporalAmount)}
 * throws an {@link ArithmeticException} when the result overflows {@code int}.
 */
public class TestWeeks_test_plus_TemporalAmount_overflowTooBig {

    @Test
    public void test_plus_TemporalAmount_overflowTooBig() {
        Weeks nearMaxValue = Weeks.of(Integer.MAX_VALUE - 1);

        // Adding 2 weeks pushes the total past Integer.MAX_VALUE, so it must overflow.
        assertThrows(ArithmeticException.class, () -> nearMaxValue.plus(Weeks.of(2)));
    }
}
