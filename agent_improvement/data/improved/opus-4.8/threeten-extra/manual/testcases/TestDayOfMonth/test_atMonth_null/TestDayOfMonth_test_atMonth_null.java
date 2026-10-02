package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focusing on {@code atMonth} null handling
 * (with the surrounding {@code now} cases kept for context).
 */
public class TestDayOfMonth_test_atMonth_null {

    /** A fixed day-of-month used as the subject under test. */
    private static final DayOfMonth DAY_12 = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_returnsCurrentDayOfMonthInDefaultZone() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZoneId_returnsCurrentDayOfMonthInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // atMonth(Month)
    //-----------------------------------------------------------------------
    @Test
    public void atMonth_withNullMonth_throwsNullPointerException() {
        Month nullMonth = null;
        assertThrows(NullPointerException.class, () -> DAY_12.atMonth(nullMonth));
    }
}
