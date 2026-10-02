package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_plus_Period {

    @Test
    public void test_plus_Period() {
        // Sep 2 1752 is the last pre-cutover date; adding 1 month and 3 days lands safely after the gap
        BritishCutoverDate startAtPreCutover = BritishCutoverDate.of(1752, 9, 2);
        ChronoPeriod oneMonthThreeDays = BritishCutoverChronology.INSTANCE.period(0, 1, 3);
        assertEquals(BritishCutoverDate.of(1752, 10, 5), startAtPreCutover.plus(oneMonthThreeDays));

        // Adding 1 month to Aug 12 yields Sep 12 Julian, which falls in the 11-day cutover gap;
        // the gap date is leniently shifted forward by 11 days to Sep 23
        BritishCutoverDate startBeforeCutoverMonth = BritishCutoverDate.of(1752, 8, 12);
        ChronoPeriod oneMonth = BritishCutoverChronology.INSTANCE.period(0, 1, 0);
        assertEquals(BritishCutoverDate.of(1752, 9, 23), startBeforeCutoverMonth.plus(oneMonth));

        // Post-cutover (Gregorian) date: adding 2 months and 3 days is a straightforward calculation
        BritishCutoverDate startPostCutover = BritishCutoverDate.of(2014, 5, 26);
        ChronoPeriod twoMonthsThreeDays = BritishCutoverChronology.INSTANCE.period(0, 2, 3);
        assertEquals(BritishCutoverDate.of(2014, 7, 29), startPostCutover.plus(twoMonthsThreeDays));
    }
}
