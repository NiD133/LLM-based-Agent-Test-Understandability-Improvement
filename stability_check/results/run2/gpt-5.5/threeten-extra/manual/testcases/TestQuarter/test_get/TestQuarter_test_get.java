package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_get {

    @Test
    public void test_get() {
        assertEquals(1, Quarter.Q1.get(QUARTER_OF_YEAR));
        assertEquals(2, Quarter.Q2.get(QUARTER_OF_YEAR));
        assertEquals(3, Quarter.Q3.get(QUARTER_OF_YEAR));
        assertEquals(4, Quarter.Q4.get(QUARTER_OF_YEAR));
    }
}
