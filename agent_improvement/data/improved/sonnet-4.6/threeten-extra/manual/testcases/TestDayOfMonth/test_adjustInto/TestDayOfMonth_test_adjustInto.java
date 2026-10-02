package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_adjustInto {

    // January always has 31 days, making it safe for testing all valid day-of-month values (1–31).
    private static final int MAX_LENGTH = 31;

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
    public void test_adjustInto() {
        LocalDate januaryFirst2007 = LocalDate.of(2007, 1, 1);
        for (int day = 1; day <= MAX_LENGTH; day++) {
            LocalDate expectedDate = LocalDate.of(2007, 1, day);
            Temporal result = DayOfMonth.of(day).adjustInto(januaryFirst2007);
            assertEquals(expectedDate, result);
        }
    }
}
