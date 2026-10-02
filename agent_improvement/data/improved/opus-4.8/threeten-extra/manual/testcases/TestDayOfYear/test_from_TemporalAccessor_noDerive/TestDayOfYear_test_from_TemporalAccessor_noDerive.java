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
 * Tests for {@link DayOfYear#from(java.time.temporal.TemporalAccessor)} and the
 * {@code now(...)} factory methods.
 */
public class TestDayOfYear_test_from_TemporalAccessor_noDerive {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    // Retried because the day-of-year could roll over between the two reads of "now".
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    // Retried because the day-of-year could roll over between the two reads of "now".
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // from(TemporalAccessor)
    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_noDerive() {
        // A LocalTime carries no day-of-year information, so it cannot be converted.
        assertThrows(DateTimeException.class, () -> DayOfYear.from(LocalTime.NOON));
    }
}
