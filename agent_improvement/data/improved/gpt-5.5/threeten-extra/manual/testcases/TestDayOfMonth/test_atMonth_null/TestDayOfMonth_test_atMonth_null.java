package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_atMonth_null {

    private static final DayOfMonth DAY_TWELVE = DayOfMonth.of(12);
    private static final ZoneId TOKYO = ZoneId.of("Asia/Tokyo");

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        assertEquals(LocalDate.now(TOKYO).getDayOfMonth(), DayOfMonth.now(TOKYO).getValue());
    }

    @Test
    public void test_atMonth_null() {
        //noinspection DataFlowIssue - testing nulls
        assertThrows(NullPointerException.class, () -> DAY_TWELVE.atMonth((Month) null));
    }
}
