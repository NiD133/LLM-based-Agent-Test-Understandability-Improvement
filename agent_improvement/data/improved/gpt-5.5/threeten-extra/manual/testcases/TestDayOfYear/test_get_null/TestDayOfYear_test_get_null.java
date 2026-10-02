package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_get_null {

    private static final DayOfYear TEST = DayOfYear.of(12);

    @Test
    public void test_get_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.get((TemporalField) null));
    }
}
