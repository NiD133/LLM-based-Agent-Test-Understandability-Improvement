package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the factory methods on {@link DayOfYear} that read the current
 * day-of-year and that convert from a {@code TemporalAccessor}.
 */
public class TestDayOfYear_test_from_TemporalAccessor_DayOfYear {

    /**
     * {@code DayOfYear.now()} must report the same day-of-year as the
     * system clock in the default time-zone.
     */
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    /**
     * {@code DayOfYear.now(ZoneId)} must report the same day-of-year as the
     * system clock in the given time-zone.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    /**
     * {@code DayOfYear.from} applied to a {@code DayOfYear} returns an
     * equal instance.
     */
    @Test
    public void test_from_TemporalAccessor_DayOfYear() {
        DayOfYear dayOfYear = DayOfYear.of(6);
        assertEquals(dayOfYear, DayOfYear.from(dayOfYear));
    }
}
