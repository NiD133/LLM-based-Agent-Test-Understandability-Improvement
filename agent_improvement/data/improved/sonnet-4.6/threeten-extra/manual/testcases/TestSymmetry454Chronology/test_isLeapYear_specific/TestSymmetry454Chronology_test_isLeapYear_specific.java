package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Symmetry454Chronology#isLeapYear(long)} for specific known years.
 *
 * Leap years in Symmetry454 satisfy: 52 > ((52 * year + 146) % 293).
 * They occur every 5 or 6 years, spread evenly across a 293-year cycle (52 leap years per cycle).
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_isLeapYear_specific {

    @Test
    public void test_isLeapYear_specific() {
        // Year 3: (52*3 + 146) % 293 = 302 % 293 = 9  → 52 > 9  → leap year
        assertTrue(Symmetry454Chronology.INSTANCE.isLeapYear(3));

        // Year 6: (52*6 + 146) % 293 = 458 % 293 = 165 → 52 > 165 → not a leap year
        assertFalse(Symmetry454Chronology.INSTANCE.isLeapYear(6));

        // Year 9: (52*9 + 146) % 293 = 614 % 293 = 28  → 52 > 28  → leap year
        assertTrue(Symmetry454Chronology.INSTANCE.isLeapYear(9));

        // Year 2000: remainder is 131 → 52 > 131 → not a leap year
        assertFalse(Symmetry454Chronology.INSTANCE.isLeapYear(2000));

        // Year 2004: remainder is 46  → 52 > 46  → leap year
        assertTrue(Symmetry454Chronology.INSTANCE.isLeapYear(2004));
    }
}
