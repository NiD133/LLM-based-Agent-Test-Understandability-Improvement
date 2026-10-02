package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on {@code getLong(TemporalField)}
 * with a null argument.
 */
public class TestDayOfYear_test_getLong_null {

    /** A fixed sample day-of-year used as the subject under test. */
    private static final DayOfYear SAMPLE_DAY_OF_YEAR = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        // DayOfYear.now() must agree with the JDK's current day-of-year.
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        // DayOfYear.now(zone) must agree with the JDK's current day-of-year in that zone.
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_getLong_null() {
        // Passing a null field must be rejected with a NullPointerException.
        assertThrows(NullPointerException.class,
                () -> SAMPLE_DAY_OF_YEAR.getLong((TemporalField) null));
    }
}
