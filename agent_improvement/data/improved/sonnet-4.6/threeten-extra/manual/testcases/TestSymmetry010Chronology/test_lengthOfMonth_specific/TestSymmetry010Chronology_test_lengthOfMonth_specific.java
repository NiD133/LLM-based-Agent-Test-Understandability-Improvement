package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_lengthOfMonth_specific {

    // In the Symmetry010 calendar, December normally has 30 days,
    // but in leap years it gains an extra week (7 days), for a total of 37 days.

    @Test
    public void test_lengthOfMonth_specific() {
        // Year 2000 is a regular (non-leap) year: December has the standard 30 days.
        Symmetry010Date regularYearDec = Symmetry010Date.of(2000, 12, 1);
        assertEquals(30, regularYearDec.lengthOfMonth());

        // Year 2004 is a leap year: December is extended by one leap week to 37 days.
        Symmetry010Date leapYearDec = Symmetry010Date.of(2004, 12, 1);
        assertEquals(37, leapYearDec.lengthOfMonth());
    }
}
