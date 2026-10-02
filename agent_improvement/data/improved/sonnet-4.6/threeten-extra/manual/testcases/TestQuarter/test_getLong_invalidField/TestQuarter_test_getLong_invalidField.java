package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_getLong_invalidField {

    @Test
    public void test_getLong_invalidField() {
        // MONTH_OF_YEAR is a ChronoField, which Quarter does not support via getLong()
        assertThrows(UnsupportedTemporalTypeException.class, () -> Quarter.Q2.getLong(MONTH_OF_YEAR));
    }
}
