package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Symmetry010Chronology#isLeapYear(long)} against hand-picked years.
 * <p>
 * In the Symmetry010 calendar a leap year carries an extra "leap week", which the
 * chronology determines from the proleptic year via the formula
 * {@code 52 > ((52 * year + 146) % 293)}. The cases below pin down a few specific
 * years (both leap and non-leap) to guard that formula against regressions.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        // Leap years: carry the extra leap week.
        assertTrue(Symmetry010Chronology.INSTANCE.isLeapYear(3));
        assertTrue(Symmetry010Chronology.INSTANCE.isLeapYear(9));
        assertTrue(Symmetry010Chronology.INSTANCE.isLeapYear(2004));

        // Non-leap years: standard 364-day length.
        assertFalse(Symmetry010Chronology.INSTANCE.isLeapYear(6));
        assertFalse(Symmetry010Chronology.INSTANCE.isLeapYear(2000));
    }
}
