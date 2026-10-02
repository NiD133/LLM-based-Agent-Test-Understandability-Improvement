package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focused on the factory method {@link DayOfYear#of(int)}.
 */
public class TestDayOfYear_test_of_int_tooHigh {

    /**
     * {@code now()} should report the same day-of-year as the current local date.
     */
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    /**
     * {@code now(ZoneId)} should report the same day-of-year as the current local date in that zone.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    /**
     * The valid day-of-year range is 1..366, so a value above 366 must be rejected.
     */
    @Test
    public void test_of_int_tooHigh() {
        int dayAboveMaximum = 367;
        assertThrows(DateTimeException.class, () -> DayOfYear.of(dayAboveMaximum));
    }
}
