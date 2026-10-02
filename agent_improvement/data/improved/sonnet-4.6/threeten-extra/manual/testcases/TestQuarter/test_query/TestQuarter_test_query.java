package org.threeten.extra;

import static java.time.temporal.IsoFields.QUARTER_YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.IsoChronology;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestQuarter_test_query {

    @Test
    @DisplayName("Quarter.Q1 query() returns correct values for all standard TemporalQueries")
    public void test_query() {
        assertEquals(IsoChronology.INSTANCE, Quarter.Q1.query(TemporalQueries.chronology()));
        assertNull(Quarter.Q1.query(TemporalQueries.localDate()));
        assertNull(Quarter.Q1.query(TemporalQueries.localTime()));
        assertNull(Quarter.Q1.query(TemporalQueries.offset()));
        assertEquals(QUARTER_YEARS, Quarter.Q1.query(TemporalQueries.precision()));
        assertNull(Quarter.Q1.query(TemporalQueries.zone()));
        assertNull(Quarter.Q1.query(TemporalQueries.zoneId()));
    }
}
