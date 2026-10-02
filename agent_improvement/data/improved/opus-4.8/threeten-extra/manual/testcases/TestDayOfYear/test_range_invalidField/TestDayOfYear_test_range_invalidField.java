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
 * Tests {@link DayOfYear#range(java.time.temporal.TemporalField)} and the related
 * factory methods, focusing on the behaviour when an unsupported field is queried.
 */
public class TestDayOfYear_test_range_invalidField {

    /** A fixed sample day-of-year used by the range test. */
    private static final DayOfYear SAMPLE_DAY = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    /**
     * DayOfYear only supports the DAY_OF_YEAR field, so asking for the range of any
     * other field (here MONTH_OF_YEAR) must fail with UnsupportedTemporalTypeException.
     */
    @Test
    public void test_range_invalidField() {
        assertThrows(UnsupportedTemporalTypeException.class, () -> SAMPLE_DAY.range(MONTH_OF_YEAR));
    }
}
