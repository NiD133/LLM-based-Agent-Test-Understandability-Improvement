package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_get_invalidField {

    @Test
    public void test_get_invalidField() {
        // Quarter only supports QUARTER_OF_YEAR; any ChronoField such as
        // MONTH_OF_YEAR must throw UnsupportedTemporalTypeException.
        assertThrows(UnsupportedTemporalTypeException.class, () -> Quarter.Q2.get(MONTH_OF_YEAR));
    }
}
