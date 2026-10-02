package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_isLeapYear_loop {

    /**
     * Returns true if the given year is a leap year in the Pax calendar.
     *
     * Pax leap year rule: a year is a leap year when its last two digits are
     * divisible by 6, are 99, or are 00 — EXCEPT that years divisible by 400
     * are NOT leap years (even though their last two digits are 00).
     */
    private static boolean isPaxLeapYear(int year) {
        int lastTwoDigits = Math.abs(year % 100);
        boolean endsWith99 = lastTwoDigits == 99;
        boolean endsWithMultipleOf6OrZero = (lastTwoDigits == 0 || lastTwoDigits % 6 == 0);
        boolean divisibleBy400 = year % 400 == 0;
        return endsWith99 || (endsWithMultipleOf6OrZero && !divisibleBy400);
    }

    @Test
    public void test_isLeapYear_loop() {
        for (int year = -500; year < 500; year++) {
            boolean expected = isPaxLeapYear(year);
            PaxDate base = PaxDate.of(year, 1, 1);

            assertEquals(expected, base.isLeapYear(),
                    "PaxDate.isLeapYear() mismatch for year " + year);
            assertEquals(expected, PaxChronology.INSTANCE.isLeapYear(year),
                    "PaxChronology.isLeapYear() mismatch for year " + year);
        }
    }
}
