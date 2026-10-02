package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZoneId;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_query {

    // DayOfMonth.of(12) is used as the representative instance for query tests
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    // -----------------------------------------------------------------------
    // now() — uses the system clock in the default time-zone
    // -----------------------------------------------------------------------

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    // -----------------------------------------------------------------------
    // now(ZoneId) — uses the system clock in the specified time-zone
    // -----------------------------------------------------------------------

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    // -----------------------------------------------------------------------
    // query(TemporalQuery) — verifies what metadata DayOfMonth exposes
    // -----------------------------------------------------------------------

    @Test
    public void test_query() {
        // DayOfMonth lives in the ISO calendar system
        assertEquals(IsoChronology.INSTANCE, TEST.query(TemporalQueries.chronology()));

        // A bare day-of-month carries no full date, time, offset, or zone information
        assertNull(TEST.query(TemporalQueries.localDate()));
        assertNull(TEST.query(TemporalQueries.localTime()));
        assertNull(TEST.query(TemporalQueries.offset()));
        assertNull(TEST.query(TemporalQueries.zone()));
        assertNull(TEST.query(TemporalQueries.zoneId()));

        // The finest unit that DayOfMonth can represent is a day
        assertEquals(ChronoUnit.DAYS, TEST.query(TemporalQueries.precision()));
    }
}
