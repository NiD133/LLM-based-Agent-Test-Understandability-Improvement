package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the factory methods of {@link DayOfMonth}: {@code now()}, {@code now(ZoneId)}
 * and the {@code from(TemporalAccessor)} conversion.
 */
public class TestDayOfMonth_test_from_TemporalAccessor_noDerive {

    //-----------------------------------------------------------------------
    // now() reflects the day-of-month of the current date in the default zone
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId) reflects the day-of-month of the current date in the given zone
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // from(TemporalAccessor) rejects a temporal that has no day-of-month to derive
    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_noDerive() {
        // LocalTime carries no date information, so a day-of-month cannot be obtained.
        assertThrows(DateTimeException.class, () -> DayOfMonth.from(LocalTime.NOON));
    }
}
