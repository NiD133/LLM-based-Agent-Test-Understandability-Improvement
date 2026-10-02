package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_get {

    /**
     * Verifies that {@link Quarter#get(java.time.temporal.TemporalField)} returns
     * the correct 1-based quarter number when queried with {@code QUARTER_OF_YEAR}:
     * Q1 → 1, Q2 → 2, Q3 → 3, Q4 → 4.
     */
    @Test
    public void test_get() {
        assertEquals(1, Quarter.Q1.get(QUARTER_OF_YEAR));
        assertEquals(2, Quarter.Q2.get(QUARTER_OF_YEAR));
        assertEquals(3, Quarter.Q3.get(QUARTER_OF_YEAR));
        assertEquals(4, Quarter.Q4.get(QUARTER_OF_YEAR));
    }
}
