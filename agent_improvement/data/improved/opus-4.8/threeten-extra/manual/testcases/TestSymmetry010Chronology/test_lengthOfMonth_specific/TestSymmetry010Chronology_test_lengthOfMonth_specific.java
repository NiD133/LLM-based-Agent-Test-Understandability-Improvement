package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link Symmetry010Date#lengthOfMonth()} for December, where the
 * Symmetry010 calendar's month length depends on whether the year is a leap year.
 * <p>
 * In a normal year December has 30 days, while in a leap year the extra
 * "leap week" is appended to December, giving it 37 days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_lengthOfMonth_specific {

    @Test
    public void test_lengthOfMonth_specific() {
        // 2000 is a normal year: December has 30 days.
        assertEquals(30, Symmetry010Date.of(2000, 12, 1).lengthOfMonth());

        // 2004 is a leap year: December absorbs the leap week, giving 37 days.
        assertEquals(37, Symmetry010Date.of(2004, 12, 1).lengthOfMonth());
    }
}
