package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests the factory methods of {@link DayOfMonth} that obtain a value from the
 * current date or from another temporal object.
 */
public class TestDayOfMonth_test_from_TemporalAccessor_DayOfMonth {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    /**
     * {@code DayOfMonth.now()} must report the same day-of-month as
     * {@code LocalDate.now()} in the default time-zone.
     * <p>
     * Retried because the two "now" reads could straddle a midnight boundary.
     */
    @RetryingTest(100)
    public void now_matchesCurrentLocalDate() {
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    /**
     * {@code DayOfMonth.now(zone)} must report the same day-of-month as
     * {@code LocalDate.now(zone)} for the same zone.
     * <p>
     * Retried because the two "now" reads could straddle a midnight boundary.
     */
    @RetryingTest(100)
    public void now_withZone_matchesCurrentLocalDateInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // from(TemporalAccessor)
    //-----------------------------------------------------------------------
    /**
     * {@code DayOfMonth.from} applied to a {@code DayOfMonth} returns an equal value.
     */
    @Test
    public void from_givenDayOfMonth_returnsEqualValue() {
        DayOfMonth dayOfMonth = DayOfMonth.of(6);
        assertEquals(dayOfMonth, DayOfMonth.from(dayOfMonth));
    }
}
