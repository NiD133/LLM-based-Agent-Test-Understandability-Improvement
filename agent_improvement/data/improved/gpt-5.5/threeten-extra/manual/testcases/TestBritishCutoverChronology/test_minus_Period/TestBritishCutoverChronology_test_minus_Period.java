package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        ChronoPeriod oneMonth = BritishCutoverChronology.INSTANCE.period(0, 1, 0);
        assertEquals(
                BritishCutoverDate.of(1752, 9, 23),
                BritishCutoverDate.of(1752, 10, 12).minus(oneMonth));

        ChronoPeriod twoMonthsAndThreeDays = BritishCutoverChronology.INSTANCE.period(0, 2, 3);
        assertEquals(
                BritishCutoverDate.of(2014, 3, 23),
                BritishCutoverDate.of(2014, 5, 26).minus(twoMonthsAndThreeDays));
    }
}
