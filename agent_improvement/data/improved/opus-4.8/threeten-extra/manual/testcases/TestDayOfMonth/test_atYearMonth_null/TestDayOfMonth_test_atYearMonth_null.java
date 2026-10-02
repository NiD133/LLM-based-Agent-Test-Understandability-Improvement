package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focused on the {@code atYearMonth} factory and
 * the current-day-of-month factories.
 */
public class TestDayOfMonth_test_atYearMonth_null {

    /** A fixed sample day-of-month used as the receiver under test. */
    private static final DayOfMonth SAMPLE_DAY = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_returnsTodaysDayOfMonth() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZone_returnsTodaysDayOfMonthInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // atYearMonth(YearMonth)
    //-----------------------------------------------------------------------
    @Test
    public void atYearMonth_withNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SAMPLE_DAY.atYearMonth((YearMonth) null));
    }
}
