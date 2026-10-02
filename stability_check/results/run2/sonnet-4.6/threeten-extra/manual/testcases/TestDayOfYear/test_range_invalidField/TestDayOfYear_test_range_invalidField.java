package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_range_invalidField {

    // DayOfYear only supports DAY_OF_YEAR; querying any other ChronoField must throw
    private static final DayOfYear TEST = DayOfYear.of(12);

    @Test
    public void test_range_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> TEST.range(MONTH_OF_YEAR));
    }
}
