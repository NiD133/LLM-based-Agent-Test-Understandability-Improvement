package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_Chronology_isLeapYear_specific {

    @Test
    public void test_Chronology_isLeapYear_specific() {
        Chronology britishCutover = BritishCutoverChronology.INSTANCE;

        assertTrue(britishCutover.isLeapYear(8));
        assertFalse(britishCutover.isLeapYear(7));
        assertFalse(britishCutover.isLeapYear(6));
        assertFalse(britishCutover.isLeapYear(5));

        assertTrue(britishCutover.isLeapYear(4));
        assertFalse(britishCutover.isLeapYear(3));
        assertFalse(britishCutover.isLeapYear(2));
        assertFalse(britishCutover.isLeapYear(1));

        assertTrue(britishCutover.isLeapYear(0));
        assertFalse(britishCutover.isLeapYear(-1));
        assertFalse(britishCutover.isLeapYear(-2));
        assertFalse(britishCutover.isLeapYear(-3));

        assertTrue(britishCutover.isLeapYear(-4));
        assertFalse(britishCutover.isLeapYear(-5));
        assertFalse(britishCutover.isLeapYear(-6));
    }
}
