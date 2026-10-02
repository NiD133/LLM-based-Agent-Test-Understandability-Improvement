package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focusing on rejecting an out-of-range month
 * value passed to {@link DayOfMonth#atMonth(int)}.
 */
public class TestDayOfMonth_test_atMonth_tooHigh {

    /** An arbitrary, valid day-of-month used as the subject under test. */
    private static final DayOfMonth DAY_12 = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesSystemClockDayOfMonth() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZone_matchesClockDayOfMonthInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // atMonth(int)
    //-----------------------------------------------------------------------
    @Test
    public void atMonth_withMonthAboveDecember_throwsDateTimeException() {
        // Months are 1 (January) to 12 (December); 13 is out of range.
        int monthAboveRange = 13;
        assertThrows(DateTimeException.class, () -> DAY_12.atMonth(monthAboveRange));
    }
}
