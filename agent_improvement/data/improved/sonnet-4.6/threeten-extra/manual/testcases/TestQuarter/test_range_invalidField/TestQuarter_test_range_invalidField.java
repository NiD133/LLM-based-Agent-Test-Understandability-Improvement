package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_range_invalidField {

    @Test
    public void test_range_invalidField() {
        // MONTH_OF_YEAR is a ChronoField; Quarter.range() must reject all ChronoFields
        // that are not QUARTER_OF_YEAR with UnsupportedTemporalTypeException.
        assertThrows(UnsupportedTemporalTypeException.class, () -> Quarter.Q1.range(MONTH_OF_YEAR));
    }
}
