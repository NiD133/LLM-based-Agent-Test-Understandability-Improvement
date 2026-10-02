package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Seconds#minus(java.time.temporal.TemporalAmount)} reports
 * integer overflow rather than silently wrapping around.
 */
public class TestSeconds_test_minus_TemporalAmount_overflowTooBig {

    @Test
    public void test_minus_TemporalAmount_overflowTooBig() {
        // Start one below the largest representable value...
        Seconds almostMax = Seconds.of(Integer.MAX_VALUE - 1);
        // ...then subtract -2, i.e. add 2, which pushes the result past Integer.MAX_VALUE.
        Seconds subtractNegativeTwo = Seconds.of(-2);

        // The overflow must be signalled with an ArithmeticException.
        assertThrows(ArithmeticException.class, () -> almostMax.minus(subtractNegativeTwo));
    }
}
