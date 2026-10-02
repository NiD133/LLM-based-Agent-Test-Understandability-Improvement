package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies the Pax leap-year rule across a wide range of proleptic years.
 * <p>
 * Per the Pax calendar, a year is a leap year when the last two digits are
 * divisible by 6, or are 99, or are 00 and the year is <b>not</b> divisible by 400.
 * The test confirms that both {@link PaxDate#isLeapYear()} and
 * {@link PaxChronology#isLeapYear(long)} agree with an independently computed
 * expectation for every year from -500 to 499.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_isLeapYear_loop {

    /**
     * Independent reference implementation of the Pax leap-year rule, used to
     * cross-check the chronology under test.
     */
    private static boolean expectedLeapYear(int year) {
        int lastTwoDigits = Math.abs(year % 100);
        return (year % 400 != 0 && (lastTwoDigits == 0 || lastTwoDigits % 6 == 0)) || lastTwoDigits == 99;
    }

    @Test
    public void test_isLeapYear_loop() {
        for (int year = -500; year < 500; year++) {
            boolean expected = expectedLeapYear(year);

            PaxDate firstDayOfYear = PaxDate.of(year, 1, 1);
            assertEquals(expected, firstDayOfYear.isLeapYear());
            assertEquals(expected, PaxChronology.INSTANCE.isLeapYear(year));
        }
    }
}
