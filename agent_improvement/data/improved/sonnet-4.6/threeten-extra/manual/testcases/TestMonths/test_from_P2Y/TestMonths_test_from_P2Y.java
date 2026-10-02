package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Months#from(java.time.temporal.TemporalAmount)} correctly converts
 * a temporal amount expressed purely in years into an equivalent number of months.
 */
public class TestMonths_test_from_P2Y {

    /**
     * Verifies that a {@link MockYearsMonths} representing 2 years and 0 months is
     * converted to 24 months (2 × 12 = 24).
     */
    @Test
    @DisplayName("from(2 years, 0 months) should equal Months.of(24)")
    public void test_from_P2Y() {
        // 2 years with no extra months → 2 × 12 = 24 months
        Months expected = Months.of(24);
        Months actual = Months.from(new MockYearsMonths(2, 0));
        assertEquals(expected, actual);
    }
}
