package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focusing on {@code get(TemporalField)} with a null field.
 */
public class TestDayOfMonth_test_get_null {

    /** A fixed sample instance representing the 12th day of the month. */
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfMonth.now() should match the day-of-month of LocalDate.now().
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        // DayOfMonth.now(zone) should match the day-of-month of LocalDate.now(zone).
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // get(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_get_null() {
        // Querying a null field must throw NullPointerException.
        assertThrows(NullPointerException.class, () -> TEST.get((TemporalField) null));
    }
}
