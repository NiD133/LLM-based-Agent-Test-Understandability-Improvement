package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_range {

    @Test
    public void test_range() {
        ValueRange expectedQuarterRange = QUARTER_OF_YEAR.range();
        ValueRange actualQuarterRange = Quarter.Q1.range(QUARTER_OF_YEAR);

        assertEquals(expectedQuarterRange, actualQuarterRange);
    }
}
