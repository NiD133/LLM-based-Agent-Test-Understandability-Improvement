package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_isLeapYear_loop {

    @Test
    public void test_Chronology_isLeapYear_loop() {
        // All years in this range predate the 1752 cutover, so Julian rules apply:
        // every year divisible by 4 is a leap year (no century exception).
        for (int year = -200; year < 200; year++) {
            boolean expectedLeapYear = (year % 4) == 0;
            BritishCutoverDate date = BritishCutoverDate.of(year, 1, 1);
            assertEquals(expectedLeapYear, date.isLeapYear(),
                    "BritishCutoverDate.isLeapYear() mismatch for year " + year);
            assertEquals(expectedLeapYear, BritishCutoverChronology.INSTANCE.isLeapYear(year),
                    "BritishCutoverChronology.isLeapYear() mismatch for year " + year);
        }
    }
}
