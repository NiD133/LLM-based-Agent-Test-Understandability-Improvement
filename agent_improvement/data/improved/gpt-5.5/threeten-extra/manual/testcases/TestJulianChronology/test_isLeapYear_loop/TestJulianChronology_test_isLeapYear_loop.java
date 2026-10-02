package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_isLeapYear_loop {

    private static final int FIRST_TESTED_YEAR = -200;
    private static final int LAST_TESTED_YEAR_EXCLUSIVE = 200;

    @Test
    public void test_isLeapYear_loop() {
        for (int year = FIRST_TESTED_YEAR; year < LAST_TESTED_YEAR_EXCLUSIVE; year++) {
            assertJulianLeapYearRule(year);
        }
    }

    private static void assertJulianLeapYearRule(int year) {
        boolean expectedLeapYear = (year % 4) == 0;
        JulianDate base = JulianDate.of(year, 1, 1);

        assertEquals(expectedLeapYear, base.isLeapYear());
        assertEquals(expectedLeapYear, JulianChronology.INSTANCE.isLeapYear(year));
    }
}
