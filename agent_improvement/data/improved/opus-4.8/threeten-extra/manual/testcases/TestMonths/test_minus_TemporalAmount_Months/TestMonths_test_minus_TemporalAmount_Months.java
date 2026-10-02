package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Months#minus(java.time.temporal.TemporalAmount)}, which subtracts
 * another {@code Months} amount and returns the resulting {@code Months}.
 */
public class TestMonths_test_minus_TemporalAmount_Months {

    @Test
    public void test_minus_TemporalAmount_Months() {
        Months five = Months.of(5);

        // Subtracting zero leaves the amount unchanged.
        assertEquals(Months.of(5), five.minus(Months.of(0)));

        // Subtracting a positive amount decreases the total.
        assertEquals(Months.of(3), five.minus(Months.of(2)));

        // Subtracting a negative amount increases the total.
        assertEquals(Months.of(7), five.minus(Months.of(-2)));

        // Subtraction is allowed right up to the int boundaries.
        assertEquals(
                Months.of(Integer.MAX_VALUE),
                Months.of(Integer.MAX_VALUE - 1).minus(Months.of(-1)));
        assertEquals(
                Months.of(Integer.MIN_VALUE),
                Months.of(Integer.MIN_VALUE + 1).minus(Months.of(1)));
    }
}
