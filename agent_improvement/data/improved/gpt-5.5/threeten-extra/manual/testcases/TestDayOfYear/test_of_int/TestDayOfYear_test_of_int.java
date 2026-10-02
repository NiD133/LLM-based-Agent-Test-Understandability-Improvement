package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_of_int {

    private static final int LEAP_YEAR_LENGTH = 366;

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");

        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    @Test
    public void test_of_int() {
        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);

            assertEquals(dayOfYear, test.getValue());
            assertSame(test, DayOfYear.of(dayOfYear));
        }
    }
}
