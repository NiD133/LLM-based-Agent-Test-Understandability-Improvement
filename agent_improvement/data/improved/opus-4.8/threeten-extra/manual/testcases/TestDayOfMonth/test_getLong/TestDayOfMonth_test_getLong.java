package org.threeten.extra;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests how {@link DayOfMonth} exposes its value, focusing on
 * {@link DayOfMonth#getLong(java.time.temporal.TemporalField)}.
 */
public class TestDayOfMonth_test_getLong {

    /** A fixed instance representing the 12th day of the month. */
    private static final DayOfMonth DAY_12 = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesCurrentDayOfMonthInDefaultZone() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_matchesCurrentDayOfMonthInGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // getLong(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void getLong_returnsDayOfMonthValue() {
        assertEquals(12L, DAY_12.getLong(DAY_OF_MONTH));
    }
}
