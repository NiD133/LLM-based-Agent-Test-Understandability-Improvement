package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_isLeapYear_specific {

    private static final InternationalFixedChronology CHRONOLOGY = InternationalFixedChronology.INSTANCE;

    @Test
    public void test_isLeapYear_specific() {
        assertLeapYear(400);
        assertCommonYear(100);
        assertLeapYear(4);
        assertCommonYear(3);
        assertCommonYear(2);
        assertCommonYear(1);
    }

    private void assertLeapYear(int year) {
        assertTrue(CHRONOLOGY.isLeapYear(year));
    }

    private void assertCommonYear(int year) {
        assertFalse(CHRONOLOGY.isLeapYear(year));
    }
}
