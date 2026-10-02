package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#toString()}, which renders each quarter as its short name (Q1..Q4).
 */
public class TestQuarter_test_toString {

    @Test
    public void toString_returnsShortQuarterName() {
        assertEquals("Q1", Quarter.Q1.toString());
        assertEquals("Q2", Quarter.Q2.toString());
        assertEquals("Q3", Quarter.Q3.toString());
        assertEquals("Q4", Quarter.Q4.toString());
    }
}
