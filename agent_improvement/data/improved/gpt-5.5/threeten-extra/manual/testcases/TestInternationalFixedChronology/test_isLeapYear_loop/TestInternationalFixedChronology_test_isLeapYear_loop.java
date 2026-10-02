package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_isLeapYear_loop {

    private static final int FIRST_SUPPORTED_YEAR = 1;
    private static final int FIRST_UNTESTED_YEAR = 500;
    private static final int DAYS_IN_COMMON_YEAR = 365;
    private static final int DAYS_IN_LEAP_YEAR = 366;

    @Test
    public void test_isLeapYear_loop() {
        IntPredicate isLeapYear = year -> {
            return ((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0);
        };

        for (int year = FIRST_SUPPORTED_YEAR; year < FIRST_UNTESTED_YEAR; year++) {
            InternationalFixedDate base = InternationalFixedDate.of(year, 1, 1);

            assertEquals(isLeapYear.test(year), base.isLeapYear());
            assertEquals(isLeapYear.test(year) ? DAYS_IN_LEAP_YEAR : DAYS_IN_COMMON_YEAR, base.lengthOfYear());
            assertEquals(isLeapYear.test(year), InternationalFixedChronology.INSTANCE.isLeapYear(year));
        }
    }
}
