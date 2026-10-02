package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_get_null {

    private static final DayOfMonth DAY_OF_MONTH = DayOfMonth.of(12);

    @Test
    public void test_get_null() {
        TemporalField nullField = null;

        assertThrows(
                NullPointerException.class,
                () -> DAY_OF_MONTH.get(nullField));
    }
}
