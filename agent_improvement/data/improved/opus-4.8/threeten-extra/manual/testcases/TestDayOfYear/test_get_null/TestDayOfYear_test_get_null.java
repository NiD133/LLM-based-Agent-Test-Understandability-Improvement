package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfYear#get(TemporalField)} with a {@code null} field.
 */
public class TestDayOfYear_test_get_null {

    /** An arbitrary day-of-year used as the subject under test. */
    private static final DayOfYear TEST = DayOfYear.of(12);

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
    @Test
    public void test_get_null() {
        // Passing a null field to get(TemporalField) must throw NullPointerException.
        assertThrows(NullPointerException.class, () -> TEST.get((TemporalField) null));
    }
}
