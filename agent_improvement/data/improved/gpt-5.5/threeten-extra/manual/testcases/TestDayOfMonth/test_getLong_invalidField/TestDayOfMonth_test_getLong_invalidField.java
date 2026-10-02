package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_getLong_invalidField {

    private static final DayOfMonth TEST_DAY = DayOfMonth.of(12);

    @Test
    public void test_getLong_invalidField() {
        assertThrows(
                UnsupportedTemporalTypeException.class,
                () -> TEST_DAY.getLong(MONTH_OF_YEAR));
    }
}
