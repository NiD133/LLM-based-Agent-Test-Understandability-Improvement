package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.time.temporal.UnsupportedTemporalTypeException;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth#getLong(java.time.temporal.TemporalField)} with an unsupported field,
 * alongside the {@code now()} factory methods carried over from the original suite.
 */
public class TestDayOfMonth_test_getLong_invalidField2 {

    /** A fixed sample instance representing the 12th day of the month. */
    private static final DayOfMonth DAY_12 = DayOfMonth.of(12);

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
    public void test_getLong_invalidField2() {
        // DAY_OF_QUARTER is not a field that a day-of-month can supply, so getLong must reject it.
        assertThrows(UnsupportedTemporalTypeException.class, () -> DAY_12.getLong(IsoFields.DAY_OF_QUARTER));
    }
}
