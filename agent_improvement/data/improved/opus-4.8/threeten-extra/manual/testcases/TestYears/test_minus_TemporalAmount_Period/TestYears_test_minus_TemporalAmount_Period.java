package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Years#minus(java.time.temporal.TemporalAmount)} when the amount
 * to subtract is supplied as a {@link Period} expressed purely in years.
 */
public class TestYears_test_minus_TemporalAmount_Period {

    @Test
    public void minus_periodOfYears_subtractsTheYears() {
        Years fiveYears = Years.of(5);

        // Subtracting zero years leaves the value unchanged.
        assertEquals(Years.of(5), fiveYears.minus(Period.ofYears(0)));

        // Subtracting a positive period reduces the value: 5 - 2 = 3.
        assertEquals(Years.of(3), fiveYears.minus(Period.ofYears(2)));

        // Subtracting a negative period increases the value: 5 - (-2) = 7.
        assertEquals(Years.of(7), fiveYears.minus(Period.ofYears(-2)));

        // Subtracting a negative period may reach Integer.MAX_VALUE without overflow.
        assertEquals(
                Years.of(Integer.MAX_VALUE),
                Years.of(Integer.MAX_VALUE - 1).minus(Period.ofYears(-1)));

        // Subtracting a positive period may reach Integer.MIN_VALUE without overflow.
        assertEquals(
                Years.of(Integer.MIN_VALUE),
                Years.of(Integer.MIN_VALUE + 1).minus(Period.ofYears(1)));
    }
}
