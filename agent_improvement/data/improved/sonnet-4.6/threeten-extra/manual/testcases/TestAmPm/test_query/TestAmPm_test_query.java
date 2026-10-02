package org.threeten.extra;

import static java.time.temporal.ChronoUnit.HALF_DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_query {

    // AmPm only handles precision(); all other standard queries return null.
    @Test
    public void test_query() {
        assertNull(AmPm.AM.query(TemporalQueries.chronology()));
        assertNull(AmPm.AM.query(TemporalQueries.localDate()));
        assertNull(AmPm.AM.query(TemporalQueries.localTime()));
        assertNull(AmPm.AM.query(TemporalQueries.offset()));
        assertEquals(HALF_DAYS, AmPm.AM.query(TemporalQueries.precision()));
        assertNull(AmPm.AM.query(TemporalQueries.zone()));
        assertNull(AmPm.AM.query(TemporalQueries.zoneId()));
    }
}
