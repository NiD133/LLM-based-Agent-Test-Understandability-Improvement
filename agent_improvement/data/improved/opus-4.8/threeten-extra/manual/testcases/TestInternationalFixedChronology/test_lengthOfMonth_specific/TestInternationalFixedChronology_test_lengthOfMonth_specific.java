package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link InternationalFixedDate#lengthOfMonth()} for the calendar's two
 * special 29-day months.
 * <p>
 * In the International Fixed calendar every regular month has 28 days. Two months are
 * one day longer because they carry an extra "blank" day:
 * <ul>
 *   <li>month 13 always has 29 days (the 28 days plus the closing Year Day);</li>
 *   <li>month 6 has 29 days only in leap years (the 28 days plus the Leap Day).</li>
 * </ul>
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_lengthOfMonth_specific {

    @Test
    public void test_lengthOfMonth_specific() {
        // Month 13 always ends with the Year Day, so it is 29 days long
        // regardless of whether the year is a leap year (1900 is not, 2000 is).
        assertEquals(29, InternationalFixedDate.of(1900, 13, 29).lengthOfMonth());
        assertEquals(29, InternationalFixedDate.of(2000, 13, 29).lengthOfMonth());

        // Month 6 gains the Leap Day only in leap years; 2000 is a leap year.
        assertEquals(29, InternationalFixedDate.of(2000, 6, 29).lengthOfMonth());
    }
}
