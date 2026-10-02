package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#from(java.time.temporal.TemporalAmount)} for a zero-month period.
 */
public class TestMonths_test_from_Period_P0M {

    @Test
    public void from_zeroMonthPeriod_returnsZeroMonths() {
        Period zeroMonthPeriod = Period.ofMonths(0);

        Months result = Months.from(zeroMonthPeriod);

        assertEquals(Months.of(0), result);
    }
}
