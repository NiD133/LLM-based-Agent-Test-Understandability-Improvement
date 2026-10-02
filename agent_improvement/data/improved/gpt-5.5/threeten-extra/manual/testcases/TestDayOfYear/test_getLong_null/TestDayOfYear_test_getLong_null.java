package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_getLong_null {

    private static final DayOfYear TEST = DayOfYear.of(12);

    @Test
    public void test_getLong_null() {
        assertThrows(
                NullPointerException.class,
                () -> TEST.getLong((TemporalField) null));
    }
}
