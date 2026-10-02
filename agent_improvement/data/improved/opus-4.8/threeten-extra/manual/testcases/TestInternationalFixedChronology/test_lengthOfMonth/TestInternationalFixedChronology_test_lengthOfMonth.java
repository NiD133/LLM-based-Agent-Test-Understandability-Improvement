package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link InternationalFixedDate#lengthOfMonth()}.
 * <p>
 * In the International Fixed calendar a year has 13 months. A standard month has
 * 28 days. Two months can hold an extra 29th day:
 * <ul>
 *   <li>month 13 always ends with the "Year Day" (day 29), so month 13 has 29 days;</li>
 *   <li>month 6 gains a "Leap Day" (day 29) only in a leap year, so it has 29 days
 *       in leap years and 28 days otherwise.</li>
 * </ul>
 * Leap years follow the Gregorian rule, so 1900 is a common year and 1904 is a leap year.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_lengthOfMonth {

    @Test
    public void standardMonthsInCommonYearHave28Days() {
        // 1900 is a common year: every one of the first 12 months has 28 days.
        for (int month = 1; month <= 12; month++) {
            InternationalFixedDate date = InternationalFixedDate.of(1900, month, 28);
            assertEquals(28, date.lengthOfMonth(),
                    "month " + month + " of common year 1900 should have 28 days");
        }
    }

    @Test
    public void lastMonthHas29DaysBecauseOfYearDay() {
        // Month 13 always includes the Year Day (day 29), regardless of leap status.
        InternationalFixedDate yearDay = InternationalFixedDate.of(1900, 13, 29);
        assertEquals(29, yearDay.lengthOfMonth());
    }

    @Test
    public void monthSixHas29DaysInLeapYearBecauseOfLeapDay() {
        // Month 6 gains the Leap Day (day 29) only in a leap year; 1904 is a leap year.
        InternationalFixedDate leapDay = InternationalFixedDate.of(1904, 6, 29);
        assertEquals(29, leapDay.lengthOfMonth());
    }
}
