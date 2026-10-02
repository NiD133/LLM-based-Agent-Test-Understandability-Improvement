package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_adjustInto {

    private static final int MAX_VALID_DAY_OF_MONTH = 31;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_adjustInto() {
        LocalDate firstDayOfJanuary = LocalDate.of(2007, 1, 1);
        LocalDate expectedDate = firstDayOfJanuary;

        for (int dayOfMonth = 1; dayOfMonth <= MAX_VALID_DAY_OF_MONTH; dayOfMonth++) {
            Temporal adjustedDate = DayOfMonth.of(dayOfMonth).adjustInto(firstDayOfJanuary);
            assertEquals(expectedDate, adjustedDate);
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
