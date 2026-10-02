package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_range {

    @Test
    public void test_range() {
        // Quarter.range(QUARTER_OF_YEAR) must match the canonical field range (1..4)
        assertEquals(QUARTER_OF_YEAR.range(), Quarter.Q1.range(QUARTER_OF_YEAR));
    }
}
