package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_get_invalidField {

    // Half only supports HALF_OF_YEAR; querying any ChronoField (e.g. MONTH_OF_YEAR)
    // must throw UnsupportedTemporalTypeException.
    @Test
    public void test_get_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> Half.H2.get(MONTH_OF_YEAR));
    }
}
