package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the British cutover calendar reports leap years consistently,
 * both through a concrete date and through the chronology itself.
 */
public class TestBritishCutoverChronology_test_Chronology_isLeapYear_loop {

    @Test
    public void test_Chronology_isLeapYear_loop() {
        for (int year = -200; year < 200; year++) {
            // In the proleptic year range under test, leap years are exactly the multiples of 4.
            boolean expectedLeapYear = (year % 4) == 0;

            BritishCutoverDate firstOfYear = BritishCutoverDate.of(year, 1, 1);
            assertEquals(expectedLeapYear, firstOfYear.isLeapYear());
            assertEquals(expectedLeapYear, BritishCutoverChronology.INSTANCE.isLeapYear(year));
        }
    }
}
