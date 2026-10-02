package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Months#from(java.time.temporal.TemporalAmount)} converts a
 * two-month {@link Period} into the equivalent {@code Months} value.
 */
public class TestMonths_test_from_Period_P2M {

    @Test
    public void from_periodOfTwoMonths_returnsTwoMonths() {
        Months converted = Months.from(Period.ofMonths(2));

        assertEquals(Months.of(2), converted);
    }
}
