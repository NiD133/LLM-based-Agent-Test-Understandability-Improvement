package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoChronology;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Quarter#query(java.time.temporal.TemporalQuery)}.
 * <p>
 * A {@code Quarter} only carries chronology and precision information, so it
 * answers the corresponding standard queries and returns {@code null} for every
 * date, time and zone related query.
 */
public class TestQuarter_test_query {

    @Test
    public void test_query() {
        // Quarter is part of the ISO calendar system.
        assertEquals(IsoChronology.INSTANCE, Quarter.Q1.query(TemporalQueries.chronology()));
        // Its temporal precision is measured in quarter-years.
        assertEquals(QUARTER_YEARS, Quarter.Q1.query(TemporalQueries.precision()));

        // A Quarter holds no date, time or zone, so these queries return null.
        assertEquals(null, Quarter.Q1.query(TemporalQueries.localDate()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.localTime()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.offset()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.zone()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.zoneId()));
    }
}
