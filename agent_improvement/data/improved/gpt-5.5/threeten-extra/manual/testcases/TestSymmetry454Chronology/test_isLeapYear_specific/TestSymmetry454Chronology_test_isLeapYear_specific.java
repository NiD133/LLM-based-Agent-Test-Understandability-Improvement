package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        assertTrue(Symmetry454Chronology.INSTANCE.isLeapYear(3), "Year 3 should contain a leap week");
        assertFalse(Symmetry454Chronology.INSTANCE.isLeapYear(6), "Year 6 should be a standard 364-day year");
        assertTrue(Symmetry454Chronology.INSTANCE.isLeapYear(9), "Year 9 should contain a leap week");
        assertFalse(Symmetry454Chronology.INSTANCE.isLeapYear(2000), "Year 2000 should be a standard 364-day year");
        assertTrue(Symmetry454Chronology.INSTANCE.isLeapYear(2004), "Year 2004 should contain a leap week");
    }
}
