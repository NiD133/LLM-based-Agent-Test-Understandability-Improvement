package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focused on {@link DayOfMonth#getLong(TemporalField)}
 * rejecting a {@code null} field.
 */
public class TestDayOfMonth_test_getLong_null {

    /** A fixed sample day-of-month used as the subject under test. */
    private static final DayOfMonth SAMPLE_DAY = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesCurrentDayInDefaultZone() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_matchesCurrentDayInGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // getLong(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void getLong_nullField_throwsNullPointerException() {
        // Passing a null field is a programming error and must be rejected.
        assertThrows(NullPointerException.class, () -> SAMPLE_DAY.getLong((TemporalField) null));
    }
}
