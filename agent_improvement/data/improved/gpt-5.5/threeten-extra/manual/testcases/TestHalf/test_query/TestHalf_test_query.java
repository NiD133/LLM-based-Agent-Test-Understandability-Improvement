package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.threeten.extra.TemporalFields.HALF_YEARS;

import java.time.chrono.IsoChronology;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;

public class TestHalf_test_query {

    @Test
    public void test_query() {
        assertEquals(IsoChronology.INSTANCE, Half.H1.query(TemporalQueries.chronology()));
        assertNull(Half.H1.query(TemporalQueries.localDate()));
        assertNull(Half.H1.query(TemporalQueries.localTime()));
        assertNull(Half.H1.query(TemporalQueries.offset()));
        assertEquals(HALF_YEARS, Half.H1.query(TemporalQueries.precision()));
        assertNull(Half.H1.query(TemporalQueries.zone()));
        assertNull(Half.H1.query(TemporalQueries.zoneId()));
    }
}
