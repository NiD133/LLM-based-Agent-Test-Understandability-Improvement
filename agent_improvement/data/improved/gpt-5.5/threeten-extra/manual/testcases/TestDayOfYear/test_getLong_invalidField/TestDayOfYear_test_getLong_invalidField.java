package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_getLong_invalidField {

    private static final DayOfYear DAY_OF_YEAR = DayOfYear.of(12);

    @Test
    public void test_getLong_invalidField() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> DAY_OF_YEAR.getLong(MONTH_OF_YEAR));
    }
}
