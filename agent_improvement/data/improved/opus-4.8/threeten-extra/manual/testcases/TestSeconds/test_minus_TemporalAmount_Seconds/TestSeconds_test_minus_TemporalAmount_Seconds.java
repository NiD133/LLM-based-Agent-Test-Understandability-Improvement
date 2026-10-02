package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#minus(java.time.temporal.TemporalAmount)}.
 * <p>
 * Each case subtracts another {@code Seconds} amount from a base amount and
 * verifies the resulting {@code Seconds}, including the boundary cases where the
 * subtraction reaches {@link Integer#MAX_VALUE} and {@link Integer#MIN_VALUE}.
 */
public class TestSeconds_test_minus_TemporalAmount_Seconds {

    @Test
    public void test_minus_TemporalAmount_Seconds() {
        Seconds base = Seconds.of(5);

        // subtracting zero leaves the amount unchanged
        assertEquals(Seconds.of(5), base.minus(Seconds.of(0)));
        // subtracting a positive amount decreases the result
        assertEquals(Seconds.of(3), base.minus(Seconds.of(2)));
        // subtracting a negative amount increases the result
        assertEquals(Seconds.of(7), base.minus(Seconds.of(-2)));

        // subtraction may reach the int boundaries without overflow
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE - 1).minus(Seconds.of(-1)));
        assertEquals(Seconds.of(Integer.MIN_VALUE), Seconds.of(Integer.MIN_VALUE + 1).minus(Seconds.of(1)));
    }
}
