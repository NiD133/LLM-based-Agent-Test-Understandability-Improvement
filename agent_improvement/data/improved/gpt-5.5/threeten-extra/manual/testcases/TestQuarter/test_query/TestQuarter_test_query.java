package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoChronology;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;

public class TestQuarter_test_query {

    @Test
    public void test_query() {
        assertEquals(IsoChronology.INSTANCE, Quarter.Q1.query(TemporalQueries.chronology()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.localDate()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.localTime()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.offset()));
        assertEquals(QUARTER_YEARS, Quarter.Q1.query(TemporalQueries.precision()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.zone()));
        assertEquals(null, Quarter.Q1.query(TemporalQueries.zoneId()));
    }
}
