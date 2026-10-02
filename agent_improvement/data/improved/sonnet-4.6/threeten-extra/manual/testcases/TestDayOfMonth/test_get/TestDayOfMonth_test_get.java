package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth} covering {@code now()}, {@code now(ZoneId)}, and {@code get(TemporalField)}.
 */
public class TestDayOfMonth_test_get {

    // A representative DayOfMonth used by test_get to verify field retrieval
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    // -----------------------------------------------------------------------
    // now() — current day in the default time-zone
    // -----------------------------------------------------------------------

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    // -----------------------------------------------------------------------
    // now(ZoneId) — current day in an explicit time-zone
    // -----------------------------------------------------------------------

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    // -----------------------------------------------------------------------
    // get(TemporalField) — returns the day-of-month value for DAY_OF_MONTH
    // -----------------------------------------------------------------------

    @Test
    public void test_get() {
        // DayOfMonth.of(12) holds the value 12; querying DAY_OF_MONTH must return that value
        assertEquals(12, TEST.get(DAY_OF_MONTH));
    }
}
