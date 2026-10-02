package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for the factory methods of {@link DayOfMonth}.
 */
public class TestDayOfMonth_test_from_TemporalAccessor_null {

    /**
     * {@code now()} should report the day-of-month of today's date in the
     * default time-zone.
     */
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    /**
     * {@code now(ZoneId)} should report the day-of-month of today's date in the
     * given time-zone.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now(tokyo).getValue());
    }

    /**
     * {@code from(TemporalAccessor)} should reject a null temporal with a
     * {@link NullPointerException}.
     */
    @Test
    public void test_from_TemporalAccessor_null() {
        TemporalAccessor nullTemporal = null;
        assertThrows(NullPointerException.class, () -> DayOfMonth.from(nullTemporal));
    }
}
