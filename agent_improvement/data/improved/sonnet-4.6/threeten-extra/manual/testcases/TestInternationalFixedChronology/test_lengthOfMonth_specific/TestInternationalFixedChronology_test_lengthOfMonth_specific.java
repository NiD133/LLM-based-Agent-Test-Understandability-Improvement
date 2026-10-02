package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_lengthOfMonth_specific {

    @Test
    public void test_lengthOfMonth_specific() {
        // Year Day (month 13, day 29) always has 29 days even in non-leap years
        InternationalFixedDate yearDay1900 = InternationalFixedDate.of(1900, 13, 29);
        assertEquals(29, yearDay1900.lengthOfMonth());

        // Year 2000 is a leap year (divisible by 400); month 13 still has 29 days
        InternationalFixedDate yearDay2000 = InternationalFixedDate.of(2000, 13, 29);
        assertEquals(29, yearDay2000.lengthOfMonth());

        // Month 6 gains a Leap Day (day 29) in leap years, so lengthOfMonth is 29
        InternationalFixedDate leapDay2000 = InternationalFixedDate.of(2000, 6, 29);
        assertEquals(29, leapDay2000.lengthOfMonth());
    }
}
