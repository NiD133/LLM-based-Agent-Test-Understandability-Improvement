package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Symmetry454Date#lengthOfMonth()} for December, the month whose
 * length depends on whether the year is a leap year.
 * <p>
 * In the Symmetry454 calendar a standard month has 28 days. A leap year adds an
 * extra "leap week" to December, so December has 28 days in a common year and
 * 35 days in a leap year.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_lengthOfMonth_specific {

    @Test
    public void test_lengthOfMonth_specific() {
        // 2000 is a common year: December has the standard 28 days.
        assertEquals(28, Symmetry454Date.of(2000, 12, 28).lengthOfMonth());
        // 2004 is a leap year: December gains the leap week, reaching 35 days.
        assertEquals(35, Symmetry454Date.of(2004, 12, 28).lengthOfMonth());
    }
}
