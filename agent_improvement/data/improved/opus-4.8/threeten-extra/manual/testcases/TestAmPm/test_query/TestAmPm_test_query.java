package org.threeten.extra;

import static java.time.temporal.ChronoUnit.HALF_DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link AmPm} responds to the standard {@link TemporalQueries}.
 * <p>
 * {@code AmPm} only carries half-day information, so it supports just the
 * {@link TemporalQueries#precision() precision} query (returning {@code HALF_DAYS}).
 * Every other standard query has no relevant data to offer and must return {@code null}.
 */
public class TestAmPm_test_query {

    @Test
    public void test_query() {
        // The only meaningful query: AmPm's precision is a half-day.
        assertEquals(HALF_DAYS, AmPm.AM.query(TemporalQueries.precision()));

        // AmPm holds no chronology, date, time, offset, or zone information,
        // so all remaining standard queries return null.
        assertEquals(null, AmPm.AM.query(TemporalQueries.chronology()));
        assertEquals(null, AmPm.AM.query(TemporalQueries.localDate()));
        assertEquals(null, AmPm.AM.query(TemporalQueries.localTime()));
        assertEquals(null, AmPm.AM.query(TemporalQueries.offset()));
        assertEquals(null, AmPm.AM.query(TemporalQueries.zone()));
        assertEquals(null, AmPm.AM.query(TemporalQueries.zoneId()));
    }
}
