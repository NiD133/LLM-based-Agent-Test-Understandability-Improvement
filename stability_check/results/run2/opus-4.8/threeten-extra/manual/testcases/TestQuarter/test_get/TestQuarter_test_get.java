package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#get(java.time.temporal.TemporalField)} for the
 * {@code QUARTER_OF_YEAR} field.
 * <p>
 * Each quarter reports its own ISO number (Q1 -> 1, ... Q4 -> 4).
 */
public class TestQuarter_test_get {

    @Test
    public void get_quarterOfYear_returnsIsoQuarterNumber() {
        assertEquals(1, Quarter.Q1.get(QUARTER_OF_YEAR));
        assertEquals(2, Quarter.Q2.get(QUARTER_OF_YEAR));
        assertEquals(3, Quarter.Q3.get(QUARTER_OF_YEAR));
        assertEquals(4, Quarter.Q4.get(QUARTER_OF_YEAR));
    }
}
