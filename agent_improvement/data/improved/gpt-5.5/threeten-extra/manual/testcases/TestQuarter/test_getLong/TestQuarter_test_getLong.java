package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_getLong {

    @Test
    public void test_getLong() {
        assertEquals(1, Quarter.Q1.getLong(QUARTER_OF_YEAR));
        assertEquals(2, Quarter.Q2.getLong(QUARTER_OF_YEAR));
        assertEquals(3, Quarter.Q3.getLong(QUARTER_OF_YEAR));
        assertEquals(4, Quarter.Q4.getLong(QUARTER_OF_YEAR));
    }
}
