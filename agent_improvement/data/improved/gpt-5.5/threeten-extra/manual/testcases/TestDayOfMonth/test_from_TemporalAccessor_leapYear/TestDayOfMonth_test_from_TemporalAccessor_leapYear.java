package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_from_TemporalAccessor_leapYear {

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
    public void test_from_TemporalAccessor_leapYear() {
        LocalDate date = LocalDate.of(2008, 1, 1);

        date = assertConsecutiveDayOfMonthValues(date, 31);
        date = assertConsecutiveDayOfMonthValues(date, 29);
        assertConsecutiveDayOfMonthValues(date, 31);
    }

    private LocalDate assertConsecutiveDayOfMonthValues(LocalDate date, int daysInMonth) {
        for (int expectedDayOfMonth = 1; expectedDayOfMonth <= daysInMonth; expectedDayOfMonth++) {
            assertEquals(expectedDayOfMonth, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        return date;
    }
}
