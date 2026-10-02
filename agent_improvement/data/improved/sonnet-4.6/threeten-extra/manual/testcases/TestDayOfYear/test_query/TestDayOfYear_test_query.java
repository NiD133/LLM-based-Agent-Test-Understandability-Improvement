package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_query {

    // A fixed day-of-year used as the subject under test
    private static final DayOfYear TEST = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_query() {
        // DayOfYear supports chronology and precision queries
        assertEquals(IsoChronology.INSTANCE, TEST.query(TemporalQueries.chronology()));
        assertEquals(ChronoUnit.DAYS, TEST.query(TemporalQueries.precision()));

        // DayOfYear holds no date, time, offset, or zone information
        assertNull(TEST.query(TemporalQueries.localDate()));
        assertNull(TEST.query(TemporalQueries.localTime()));
        assertNull(TEST.query(TemporalQueries.offset()));
        assertNull(TEST.query(TemporalQueries.zone()));
        assertNull(TEST.query(TemporalQueries.zoneId()));
    }
}
