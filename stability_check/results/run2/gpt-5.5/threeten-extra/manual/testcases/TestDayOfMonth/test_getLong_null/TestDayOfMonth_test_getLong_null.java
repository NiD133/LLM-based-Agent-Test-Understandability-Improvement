package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

public class TestDayOfMonth_test_getLong_null {

    private static final DayOfMonth TEST_DAY = DayOfMonth.of(12);

    @Test
    public void test_getLong_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST_DAY.getLong((TemporalField) null));
    }
}
