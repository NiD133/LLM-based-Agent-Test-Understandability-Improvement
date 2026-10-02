package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_getLong {

    @Test
    public void test_getLong() {
        assertEquals(1, Quarter.Q1.getLong(QUARTER_OF_YEAR), "Q1 should return 1 for QUARTER_OF_YEAR");
        assertEquals(2, Quarter.Q2.getLong(QUARTER_OF_YEAR), "Q2 should return 2 for QUARTER_OF_YEAR");
        assertEquals(3, Quarter.Q3.getLong(QUARTER_OF_YEAR), "Q3 should return 3 for QUARTER_OF_YEAR");
        assertEquals(4, Quarter.Q4.getLong(QUARTER_OF_YEAR), "Q4 should return 4 for QUARTER_OF_YEAR");
    }
}
