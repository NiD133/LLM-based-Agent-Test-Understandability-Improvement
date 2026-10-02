package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_get_invalidField {

    private static final DayOfMonth DAY_OF_MONTH = DayOfMonth.of(12);

    @Test
    public void test_get_invalidField() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> DAY_OF_MONTH.get(MONTH_OF_YEAR));
    }
}
