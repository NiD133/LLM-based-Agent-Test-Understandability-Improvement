package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focused on {@code getLong} with a null field.
 */
public class TestDayOfMonth_test_getLong_null {

    /** A fixed day-of-month instance shared by the tests. */
    private static final DayOfMonth DAY_12 = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesCurrentDayOfMonthInDefaultZone() {
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();

        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_matchesCurrentDayOfMonthInGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(tokyo).getDayOfMonth();

        assertEquals(expectedDayOfMonth, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // getLong(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void getLong_withNullField_throwsNullPointerException() {
        TemporalField nullField = null;

        assertThrows(NullPointerException.class, () -> DAY_12.getLong(nullField));
    }
}
