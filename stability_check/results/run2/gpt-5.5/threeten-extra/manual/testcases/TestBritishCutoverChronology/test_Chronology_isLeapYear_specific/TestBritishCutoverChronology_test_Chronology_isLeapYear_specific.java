package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_isLeapYear_specific {

    @Test
    public void test_Chronology_isLeapYear_specific() {
        assertLeapYear(8, true);
        assertLeapYear(7, false);
        assertLeapYear(6, false);
        assertLeapYear(5, false);
        assertLeapYear(4, true);
        assertLeapYear(3, false);
        assertLeapYear(2, false);
        assertLeapYear(1, false);
        assertLeapYear(0, true);
        assertLeapYear(-1, false);
        assertLeapYear(-2, false);
        assertLeapYear(-3, false);
        assertLeapYear(-4, true);
        assertLeapYear(-5, false);
        assertLeapYear(-6, false);
    }

    private static void assertLeapYear(long prolepticYear, boolean expectedLeapYear) {
        assertEquals(
                expectedLeapYear,
                BritishCutoverChronology.INSTANCE.isLeapYear(prolepticYear),
                () -> "Leap-year result for proleptic year " + prolepticYear);
    }
}
