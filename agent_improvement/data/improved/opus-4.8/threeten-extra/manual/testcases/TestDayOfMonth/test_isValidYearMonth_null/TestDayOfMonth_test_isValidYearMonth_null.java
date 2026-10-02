package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focused on {@code isValidYearMonth} with a null argument.
 */
public class TestDayOfMonth_test_isValidYearMonth_null {

    /** A fixed sample day-of-month used by the tests below. */
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // isValidYearMonth(YearMonth)
    //-----------------------------------------------------------------------
    @Test
    public void test_isValidYearMonth_null() {
        // A null year-month is never valid for any day-of-month.
        assertFalse(TEST.isValidYearMonth((YearMonth) null));
    }
}
