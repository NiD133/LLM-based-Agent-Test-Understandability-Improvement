package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#multipliedBy(int)}.
 */
public class TestYears_test_multipliedBy {

    /**
     * Multiplying five years by various scalars should yield five times the scalar,
     * preserving sign for negative multipliers.
     */
    @Test
    public void test_multipliedBy() {
        Years fiveYears = Years.of(5);

        assertEquals(Years.of(0), fiveYears.multipliedBy(0), "5 years * 0 should be 0 years");
        assertEquals(Years.of(5), fiveYears.multipliedBy(1), "5 years * 1 should be 5 years");
        assertEquals(Years.of(10), fiveYears.multipliedBy(2), "5 years * 2 should be 10 years");
        assertEquals(Years.of(15), fiveYears.multipliedBy(3), "5 years * 3 should be 15 years");
        assertEquals(Years.of(-15), fiveYears.multipliedBy(-3), "5 years * -3 should be -15 years");
    }
}
