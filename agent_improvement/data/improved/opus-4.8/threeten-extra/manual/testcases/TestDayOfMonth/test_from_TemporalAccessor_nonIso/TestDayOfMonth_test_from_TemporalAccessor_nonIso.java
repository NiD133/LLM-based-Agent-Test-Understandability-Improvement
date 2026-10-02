package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.JapaneseDate;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests how {@link DayOfMonth} is obtained from the current date and from
 * temporal objects, including non-ISO calendar systems.
 */
public class TestDayOfMonth_test_from_TemporalAccessor_nonIso {

    /**
     * {@code DayOfMonth.now()} should report the same day-of-month as the
     * current local date in the default time-zone.
     */
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    /**
     * {@code DayOfMonth.now(zone)} should report the same day-of-month as the
     * current local date in the given time-zone.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now(tokyo).getValue());
    }

    /**
     * {@code DayOfMonth.from} should convert a non-ISO temporal (a Japanese
     * date) by first converting it to a {@code LocalDate}, yielding the same
     * day-of-month as the original ISO date.
     */
    @Test
    public void test_from_TemporalAccessor_nonIso() {
        LocalDate today = LocalDate.now();
        JapaneseDate japaneseToday = JapaneseDate.from(today);
        assertEquals(today.getDayOfMonth(), DayOfMonth.from(japaneseToday).getValue());
    }
}
