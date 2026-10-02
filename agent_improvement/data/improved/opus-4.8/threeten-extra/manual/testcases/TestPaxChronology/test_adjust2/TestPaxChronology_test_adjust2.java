package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_adjust2 {

    /**
     * Adjusting to the last day of the month should land on the final day of
     * that month. Pax month 13 of leap year 2012 (the intercalary month) has
     * 7 days, so adjusting any date within it yields day 7.
     */
    @Test
    public void test_adjustToLastDayOfMonth() {
        PaxDate dateInIntercalaryMonth = PaxDate.of(2012, 13, 2);

        PaxDate lastDayOfMonth = dateInIntercalaryMonth.with(TemporalAdjusters.lastDayOfMonth());

        assertEquals(PaxDate.of(2012, 13, 7), lastDayOfMonth);
    }
}
