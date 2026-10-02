package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        assertTrue(Symmetry010Chronology.INSTANCE.isLeapYear(3));
        assertFalse(Symmetry010Chronology.INSTANCE.isLeapYear(6));
        assertTrue(Symmetry010Chronology.INSTANCE.isLeapYear(9));
        assertFalse(Symmetry010Chronology.INSTANCE.isLeapYear(2000));
        assertTrue(Symmetry010Chronology.INSTANCE.isLeapYear(2004));
    }
}
