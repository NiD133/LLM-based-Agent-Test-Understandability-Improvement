package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Symmetry454Chronology#isLeapYear(long)} against a handful of
 * hand-picked proleptic years.
 * <p>
 * In the Symmetry454 calendar a leap year carries an extra "leap week", and
 * leap years recur every 5 or 6 years according to the rule
 * {@code 52 > ((52 * year + 146) % 293)}. The cases below pin down both
 * leap and non-leap outcomes so the rule cannot drift silently.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        assertTrue(chronology.isLeapYear(3), "year 3 is a leap year");
        assertFalse(chronology.isLeapYear(6), "year 6 is not a leap year");
        assertTrue(chronology.isLeapYear(9), "year 9 is a leap year");
        assertFalse(chronology.isLeapYear(2000), "year 2000 is not a leap year");
        assertTrue(chronology.isLeapYear(2004), "year 2004 is a leap year");
    }
}
