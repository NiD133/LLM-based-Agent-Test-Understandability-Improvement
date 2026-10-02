package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_getLong_null {

    private static final DayOfMonth TEST = DayOfMonth.of(12);

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    @Test
    public void test_getLong_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> TEST.getLong((TemporalField) null));
    }
}
