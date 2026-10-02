package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.JapaneseDate;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests how {@link DayOfYear} is obtained from the current clock and from
 * non-ISO temporal objects.
 */
public class TestDayOfYear_test_from_TemporalAccessor_nonIso {

    /**
     * {@code DayOfYear.now()} must match the day-of-year of today's date in the
     * default time-zone. Retried because the clock can roll over between the two
     * "now" reads at a day boundary.
     */
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    /**
     * {@code DayOfYear.now(zone)} must match the day-of-year of today's date in
     * the given time-zone. Retried for the same day-boundary reason as above.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    /**
     * {@code DayOfYear.from} must accept a non-ISO temporal (a JapaneseDate),
     * converting it to a LocalDate and yielding the same day-of-year.
     */
    @Test
    public void test_from_TemporalAccessor_nonIso() {
        LocalDate today = LocalDate.now();
        JapaneseDate japaneseToday = JapaneseDate.from(today);
        assertEquals(today.getDayOfYear(), DayOfYear.from(japaneseToday).getValue());
    }
}
