package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;
import java.time.temporal.TemporalQuery;

import org.junit.jupiter.api.Test;

public class TestDayOfYear_test_query {

    private static final DayOfYear DAY_UNDER_TEST = DayOfYear.of(12);

    @Test
    public void test_query() {
        assertEquals(IsoChronology.INSTANCE, DAY_UNDER_TEST.query(TemporalQueries.chronology()));
        assertQueryReturnsNull(TemporalQueries.localDate());
        assertQueryReturnsNull(TemporalQueries.localTime());
        assertQueryReturnsNull(TemporalQueries.offset());
        assertEquals(ChronoUnit.DAYS, DAY_UNDER_TEST.query(TemporalQueries.precision()));
        assertQueryReturnsNull(TemporalQueries.zone());
        assertQueryReturnsNull(TemporalQueries.zoneId());
    }

    private static void assertQueryReturnsNull(TemporalQuery<?> query) {
        assertEquals(null, DAY_UNDER_TEST.query(query));
    }
}
