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
 * Tests for {@link DayOfMonth#now()}, {@link DayOfMonth#now(ZoneId)} and
 * {@link DayOfMonth#query(java.time.temporal.TemporalQuery)}.
 */
public class TestDayOfMonth_test_query {

    /** A fixed day-of-month used as the subject of the query tests. */
    private static final DayOfMonth DAY_12 = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesCurrentDayInDefaultZone() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesCurrentDayInGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // query(TemporalQuery)
    //-----------------------------------------------------------------------
    @Test
    public void query_returnsExpectedResultForEachStandardQuery() {
        // DayOfMonth directly supports the chronology and precision queries.
        assertEquals(IsoChronology.INSTANCE, DAY_12.query(TemporalQueries.chronology()));
        assertEquals(ChronoUnit.DAYS, DAY_12.query(TemporalQueries.precision()));

        // All other standard queries are not applicable and return null.
        assertEquals(null, DAY_12.query(TemporalQueries.localDate()));
        assertEquals(null, DAY_12.query(TemporalQueries.localTime()));
        assertEquals(null, DAY_12.query(TemporalQueries.offset()));
        assertEquals(null, DAY_12.query(TemporalQueries.zone()));
        assertEquals(null, DAY_12.query(TemporalQueries.zoneId()));
    }
}
