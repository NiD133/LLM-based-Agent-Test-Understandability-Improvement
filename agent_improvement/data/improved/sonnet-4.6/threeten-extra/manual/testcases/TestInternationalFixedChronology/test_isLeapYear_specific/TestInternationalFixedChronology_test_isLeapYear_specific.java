package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        // Divisible by 400: leap year
        assertTrue(InternationalFixedChronology.INSTANCE.isLeapYear(400));

        // Divisible by 100 but not 400: not a leap year
        assertFalse(InternationalFixedChronology.INSTANCE.isLeapYear(100));

        // Divisible by 4 but not 100: leap year
        assertTrue(InternationalFixedChronology.INSTANCE.isLeapYear(4));

        // Not divisible by 4: not a leap year
        assertFalse(InternationalFixedChronology.INSTANCE.isLeapYear(3));
        assertFalse(InternationalFixedChronology.INSTANCE.isLeapYear(2));
        assertFalse(InternationalFixedChronology.INSTANCE.isLeapYear(1));
    }
}
