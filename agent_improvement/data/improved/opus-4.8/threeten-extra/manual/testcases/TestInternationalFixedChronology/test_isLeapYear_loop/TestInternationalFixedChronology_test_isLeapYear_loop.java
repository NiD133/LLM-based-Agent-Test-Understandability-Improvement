package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.function.IntPredicate;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the International Fixed calendar treats leap years exactly like the
 * proleptic Gregorian calendar.
 *
 * <p>The International Fixed calendar reuses the Gregorian leap-year rule: a year is a
 * leap year when it is divisible by 4, except for years divisible by 100 that are not
 * also divisible by 400. A leap year contains 366 days (the extra Leap Day), while a
 * common year contains 365 days.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_isLeapYear_loop {

    /** The proleptic Gregorian leap-year rule, used as the expected-value oracle. */
    private static final IntPredicate IS_GREGORIAN_LEAP_YEAR =
        year -> (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0);

    private static final int COMMON_YEAR_LENGTH = 365;
    private static final int LEAP_YEAR_LENGTH = 366;

    @Test
    public void test_isLeapYear_loop() {
        // Check every year in [1, 500): a range that exercises the divisible-by-4,
        // divisible-by-100, and divisible-by-400 branches of the rule (e.g. 100, 200,
        // 300 are common; 400 is leap).
        for (int year = 1; year < 500; year++) {
            boolean expectedLeap = IS_GREGORIAN_LEAP_YEAR.test(year);
            int expectedLength = expectedLeap ? LEAP_YEAR_LENGTH : COMMON_YEAR_LENGTH;

            InternationalFixedDate firstDayOfYear = InternationalFixedDate.of(year, 1, 1);

            assertEquals(expectedLeap, firstDayOfYear.isLeapYear());
            assertEquals(expectedLength, firstDayOfYear.lengthOfYear());
            assertEquals(expectedLeap, InternationalFixedChronology.INSTANCE.isLeapYear(year));
        }
    }
}
