package org.threeten.extra;

import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focusing on the behaviour of
 * {@link DayOfMonth#getLong(java.time.temporal.TemporalField)} when queried
 * with a field that a day-of-month cannot supply.
 */
public class TestDayOfMonth_test_getLong_invalidField {

    /** A fixed day-of-month used as the subject under test. */
    private static final DayOfMonth DAY_OF_MONTH_12 = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_getLong_invalidField() {
        // MONTH_OF_YEAR is a ChronoField that a day-of-month does not support,
        // so getLong must reject it rather than return a value.
        assertThrows(UnsupportedTemporalTypeException.class, () -> DAY_OF_MONTH_12.getLong(MONTH_OF_YEAR));
    }
}
