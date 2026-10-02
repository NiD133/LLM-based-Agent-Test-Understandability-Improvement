package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#getLong(java.time.temporal.TemporalField)} for the
 * {@code QUARTER_OF_YEAR} field.
 * <p>
 * Each quarter should report its ISO-8601 numeric value (Q1 -> 1 ... Q4 -> 4).
 */
public class TestQuarter_test_getLong {

    @Test
    public void getLong_quarterOfYear_returnsNumericValueOfQuarter() {
        assertEquals(1, Quarter.Q1.getLong(QUARTER_OF_YEAR));
        assertEquals(2, Quarter.Q2.getLong(QUARTER_OF_YEAR));
        assertEquals(3, Quarter.Q3.getLong(QUARTER_OF_YEAR));
        assertEquals(4, Quarter.Q4.getLong(QUARTER_OF_YEAR));
    }
}
