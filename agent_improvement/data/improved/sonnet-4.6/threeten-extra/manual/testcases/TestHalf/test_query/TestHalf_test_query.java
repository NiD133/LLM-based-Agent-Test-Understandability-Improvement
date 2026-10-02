package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.TemporalFields.HALF_YEARS;

import java.time.chrono.IsoChronology;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;

public class TestHalf_test_query {

    // Verifies that Half.H1 returns the correct result for every standard TemporalQuery:
    // - chronology  → IsoChronology (Half is an ISO concept)
    // - precision   → HALF_YEARS (smallest supported unit)
    // - all others  → null (Half carries no date/time/zone info)
    @Test
    public void test_query() {
        assertEquals(IsoChronology.INSTANCE, Half.H1.query(TemporalQueries.chronology()));
        assertEquals(null, Half.H1.query(TemporalQueries.localDate()));
        assertEquals(null, Half.H1.query(TemporalQueries.localTime()));
        assertEquals(null, Half.H1.query(TemporalQueries.offset()));
        assertEquals(HALF_YEARS, Half.H1.query(TemporalQueries.precision()));
        assertEquals(null, Half.H1.query(TemporalQueries.zone()));
        assertEquals(null, Half.H1.query(TemporalQueries.zoneId()));
    }
}
