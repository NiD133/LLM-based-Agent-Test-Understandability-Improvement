package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#get(java.time.temporal.TemporalUnit)}.
 */
public class TestMonths_test_get {

    @Test
    public void get_withMonthsUnit_returnsTheNumberOfMonths() {
        Months sixMonths = Months.of(6);

        long amount = sixMonths.get(ChronoUnit.MONTHS);

        assertEquals(6, amount);
    }
}
