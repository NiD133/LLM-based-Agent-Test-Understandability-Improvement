package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestHalf_test_getLong_invalidField {

    @Test
    public void test_getLong_invalidField() {
        // Half only supports HALF_OF_YEAR; any ChronoField such as MONTH_OF_YEAR must be rejected
        assertThrows(UnsupportedTemporalTypeException.class, () -> Half.H2.getLong(MONTH_OF_YEAR));
    }
}
