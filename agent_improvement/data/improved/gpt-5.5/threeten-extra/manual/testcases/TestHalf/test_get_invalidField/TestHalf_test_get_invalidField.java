package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalField;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_get_invalidField {

    @Test
    public void test_get_invalidField() {
        TemporalField unsupportedField = MONTH_OF_YEAR;

        assertThrows(UnsupportedTemporalTypeException.class, () -> Half.H2.get(unsupportedField));
    }
}
