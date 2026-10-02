package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.JapaneseDate;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_from_TemporalAccessor_nonIso {

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
    public void test_from_TemporalAccessor_nonIso() {
        LocalDate isoDate = LocalDate.now();
        JapaneseDate japaneseDate = JapaneseDate.from(isoDate);
        int expectedDayOfMonth = isoDate.getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.from(japaneseDate).getValue());
    }
}
