package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_get {

    @Test
    public void test_get() {
        assertQuarterOfYear(1, Quarter.Q1);
        assertQuarterOfYear(2, Quarter.Q2);
        assertQuarterOfYear(3, Quarter.Q3);
        assertQuarterOfYear(4, Quarter.Q4);
    }

    private static void assertQuarterOfYear(int expectedQuarter, Quarter quarter) {
        assertEquals(expectedQuarter, quarter.get(QUARTER_OF_YEAR));
    }
}
