package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_minus_Period {

    @Test
    public void test_minus_Period() {
        // Subtract 1 month from a date just after the cutover gap (Oct 12 → Sep 23,
        // where Sep 3–13 were skipped in 1752, so one month back lands on Sep 23)
        ChronoPeriod oneMonth = BritishCutoverChronology.INSTANCE.period(0, 1, 0);
        BritishCutoverDate oct12_1752 = BritishCutoverDate.of(1752, 10, 12);
        BritishCutoverDate expectedSep23_1752 = BritishCutoverDate.of(1752, 9, 23);
        assertEquals(expectedSep23_1752, oct12_1752.minus(oneMonth));

        // Subtract 2 months and 3 days from a modern date (no cutover effect)
        ChronoPeriod twoMonthsThreeDays = BritishCutoverChronology.INSTANCE.period(0, 2, 3);
        BritishCutoverDate may26_2014 = BritishCutoverDate.of(2014, 5, 26);
        BritishCutoverDate expectedMar23_2014 = BritishCutoverDate.of(2014, 3, 23);
        assertEquals(expectedMar23_2014, may26_2014.minus(twoMonthsThreeDays));
    }
}
