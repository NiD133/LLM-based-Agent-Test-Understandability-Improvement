package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focused on the {@code DayOfYear.of(int)} factory
 * rejecting a day-of-year value that is below the valid range.
 */
public class TestDayOfYear_test_of_int_tooLow {

    /**
     * The current day-of-year, derived from the system clock, must match
     * {@link LocalDate#getDayOfYear()}.
     * <p>
     * Retried because the day boundary could roll over between the two reads.
     */
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    /**
     * The current day-of-year for an explicit zone must match
     * {@link LocalDate#now(ZoneId)} for the same zone.
     * <p>
     * Retried because the day boundary could roll over between the two reads.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    /**
     * {@code DayOfYear.of(0)} is below the valid range (1 to 366) and must be
     * rejected with a {@link DateTimeException}.
     */
    @Test
    public void test_of_int_tooLow() {
        int tooLowDayOfYear = 0;
        assertThrows(DateTimeException.class, () -> DayOfYear.of(tooLowDayOfYear));
    }
}
