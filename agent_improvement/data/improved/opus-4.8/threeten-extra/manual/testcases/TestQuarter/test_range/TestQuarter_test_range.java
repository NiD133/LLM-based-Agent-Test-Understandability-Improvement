package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#range(java.time.temporal.TemporalField)}.
 */
public class TestQuarter_test_range {

    /**
     * Querying the range for the {@code QUARTER_OF_YEAR} field returns that
     * field's own value range (1 to 4), independent of which quarter is asked.
     */
    @Test
    public void range_forQuarterOfYearField_returnsFieldsValueRange() {
        assertEquals(QUARTER_OF_YEAR.range(), Quarter.Q1.range(QUARTER_OF_YEAR));
    }
}
