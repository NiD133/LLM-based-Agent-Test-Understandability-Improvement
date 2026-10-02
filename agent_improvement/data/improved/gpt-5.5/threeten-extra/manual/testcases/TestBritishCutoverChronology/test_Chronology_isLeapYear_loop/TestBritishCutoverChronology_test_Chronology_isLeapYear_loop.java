package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_isLeapYear_loop {

    private static final int FIRST_TESTED_YEAR = -200;
    private static final int LAST_TESTED_YEAR_EXCLUSIVE = 200;

    @Test
    public void test_Chronology_isLeapYear_loop() {
        for (int year = FIRST_TESTED_YEAR; year < LAST_TESTED_YEAR_EXCLUSIVE; year++) {
            BritishCutoverDate firstDayOfYear = BritishCutoverDate.of(year, 1, 1);
            boolean expectedJulianLeapYear = (year % 4) == 0;

            assertEquals(expectedJulianLeapYear, firstDayOfYear.isLeapYear());
            assertEquals(expectedJulianLeapYear, BritishCutoverChronology.INSTANCE.isLeapYear(year));
        }
    }
}
