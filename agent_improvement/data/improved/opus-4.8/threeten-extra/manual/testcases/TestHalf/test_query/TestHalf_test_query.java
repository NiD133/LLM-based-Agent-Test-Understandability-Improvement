package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.threeten.extra.TemporalFields.HALF_YEARS;

import java.time.chrono.IsoChronology;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#query(java.time.temporal.TemporalQuery)}.
 * <p>
 * {@code Half} only supports the {@code chronology} and {@code precision} queries
 * directly; every other standard query delegates to the default implementation,
 * which returns {@code null} for a plain half-of-year.
 */
public class TestHalf_test_query {

    @Test
    public void test_query() {
        // Directly supported queries return meaningful values.
        assertEquals(IsoChronology.INSTANCE, Half.H1.query(TemporalQueries.chronology()));
        assertEquals(HALF_YEARS, Half.H1.query(TemporalQueries.precision()));

        // All other standard queries are not supported and resolve to null.
        assertEquals(null, Half.H1.query(TemporalQueries.localDate()));
        assertEquals(null, Half.H1.query(TemporalQueries.localTime()));
        assertEquals(null, Half.H1.query(TemporalQueries.offset()));
        assertEquals(null, Half.H1.query(TemporalQueries.zone()));
        assertEquals(null, Half.H1.query(TemporalQueries.zoneId()));
    }
}
