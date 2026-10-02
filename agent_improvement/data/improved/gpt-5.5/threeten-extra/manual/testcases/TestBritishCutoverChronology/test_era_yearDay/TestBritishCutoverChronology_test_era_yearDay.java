package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_era_yearDay {

    @Test
    public void test_era_yearDay() {
        assertYearDayResolvesToDate(1752, 1, 1752, 1, 1);

        assertYearDayResolvesToDate(1752, 244, 1752, 8, 31);
        assertYearDayResolvesToDate(1752, 246, 1752, 9, 2);
        assertYearDayResolvesToDate(1752, 247, 1752, 9, 14);
        assertYearDayResolvesToDate(1752, 257, 1752, 9, 24);
        assertYearDayResolvesToDate(1752, 258, 1752, 9, 25);

        assertYearDayResolvesToDate(1752, 355, 1752, 12, 31);
        assertYearDayResolvesToDate(2014, 1, 2014, 1, 1);
    }

    private static void assertYearDayResolvesToDate(
            int prolepticYear,
            int dayOfYear,
            int expectedYear,
            int expectedMonth,
            int expectedDay) {

        assertEquals(
                BritishCutoverDate.of(expectedYear, expectedMonth, expectedDay),
                BritishCutoverChronology.INSTANCE.dateYearDay(prolepticYear, dayOfYear));
    }
}
