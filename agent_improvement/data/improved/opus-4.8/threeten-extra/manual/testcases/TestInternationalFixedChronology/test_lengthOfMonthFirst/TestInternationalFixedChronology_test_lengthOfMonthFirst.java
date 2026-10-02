package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link InternationalFixedDate#lengthOfMonth()} for the first day of a month.
 * <p>
 * In the International Fixed calendar every ordinary month has 28 days. The two
 * exceptions are the 13th month, which always carries the extra Year Day (29 days),
 * and the 6th month in a leap year, which carries the extra Leap Day (29 days).
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_lengthOfMonthFirst {

    /**
     * Asserts that the first day of the given month reports the expected month length.
     *
     * @param year            the proleptic year
     * @param month           the month to inspect
     * @param expectedLength  the expected number of days in that month
     */
    private void assertMonthLength(int year, int month, int expectedLength) {
        InternationalFixedDate firstOfMonth = InternationalFixedDate.of(year, month, 1);
        assertEquals(expectedLength, firstOfMonth.lengthOfMonth());
    }

    @Test
    public void ordinaryMonthsHave28Days() {
        // Months 1 through 12 in a non-leap year are all 28 days long.
        assertMonthLength(1900, 1, 28);
        assertMonthLength(1900, 2, 28);
        assertMonthLength(1900, 3, 28);
        assertMonthLength(1900, 4, 28);
        assertMonthLength(1900, 5, 28);
        assertMonthLength(1900, 6, 28);
        assertMonthLength(1900, 7, 28);
        assertMonthLength(1900, 8, 28);
        assertMonthLength(1900, 9, 28);
        assertMonthLength(1900, 10, 28);
        assertMonthLength(1900, 11, 28);
        assertMonthLength(1900, 12, 28);
    }

    @Test
    public void thirteenthMonthHas29DaysForYearDay() {
        assertMonthLength(1900, 13, 29);
    }

    @Test
    public void sixthMonthHas29DaysInLeapYear() {
        assertMonthLength(1904, 6, 29);
    }
}
