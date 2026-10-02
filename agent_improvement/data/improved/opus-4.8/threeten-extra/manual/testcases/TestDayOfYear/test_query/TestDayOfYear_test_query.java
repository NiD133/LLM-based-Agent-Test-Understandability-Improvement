package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for the "now" factory methods and {@link DayOfYear#query(java.time.temporal.TemporalQuery)}.
 */
public class TestDayOfYear_test_query {

    /** Sample day-of-year used by the query tests. */
    private static final DayOfYear DAY_OF_YEAR_12 = DayOfYear.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    // RetryingTest guards against the rare clock tick that crosses midnight
    // between the two "now" reads below.
    @RetryingTest(100)
    public void test_now_usesSystemDefaultZone() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_usesGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // query(TemporalQuery)
    //-----------------------------------------------------------------------
    @Test
    public void test_query() {
        // DayOfYear answers two queries directly...
        assertEquals(IsoChronology.INSTANCE, DAY_OF_YEAR_12.query(TemporalQueries.chronology()));
        assertEquals(ChronoUnit.DAYS, DAY_OF_YEAR_12.query(TemporalQueries.precision()));

        // ...and returns null for everything it cannot supply.
        assertEquals(null, DAY_OF_YEAR_12.query(TemporalQueries.localDate()));
        assertEquals(null, DAY_OF_YEAR_12.query(TemporalQueries.localTime()));
        assertEquals(null, DAY_OF_YEAR_12.query(TemporalQueries.offset()));
        assertEquals(null, DAY_OF_YEAR_12.query(TemporalQueries.zone()));
        assertEquals(null, DAY_OF_YEAR_12.query(TemporalQueries.zoneId()));
    }
}
