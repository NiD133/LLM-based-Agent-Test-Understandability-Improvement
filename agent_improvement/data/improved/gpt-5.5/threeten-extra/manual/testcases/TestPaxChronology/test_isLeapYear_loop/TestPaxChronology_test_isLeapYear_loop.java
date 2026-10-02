package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_isLeapYear_loop {

    private static final int FIRST_TEST_YEAR = -500;
    private static final int LAST_TEST_YEAR_EXCLUSIVE = 500;

    @Test
    public void test_isLeapYear_loop() {
        for (int year = FIRST_TEST_YEAR; year < LAST_TEST_YEAR_EXCLUSIVE; year++) {
            boolean expectedLeapYear = isExpectedLeapYear(year);
            PaxDate firstDayOfYear = PaxDate.of(year, 1, 1);

            assertEquals(expectedLeapYear, firstDayOfYear.isLeapYear());
            assertEquals(expectedLeapYear, PaxChronology.INSTANCE.isLeapYear(year));
        }
    }

    private boolean isExpectedLeapYear(int year) {
        int lastTwoDigits = Math.abs(year % 100);
        return (year % 400 != 0 && (lastTwoDigits == 0 || lastTwoDigits % 6 == 0)) || lastTwoDigits == 99;
    }
}
