package org.threeten.extra;

import static java.time.temporal.ChronoUnit.HALF_DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.temporal.TemporalQuery;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;

public class TestAmPm_test_query {

    @Test
    public void test_query() {
        AmPm sample = AmPm.AM;

        TemporalQuery<?>[] queriesWithNoResult = {
                TemporalQueries.chronology(),
                TemporalQueries.localDate(),
                TemporalQueries.localTime(),
                TemporalQueries.offset(),
                TemporalQueries.zone(),
                TemporalQueries.zoneId()
        };

        for (TemporalQuery<?> query : queriesWithNoResult) {
            assertNull(sample.query(query));
        }
        assertEquals(HALF_DAYS, sample.query(TemporalQueries.precision()));
    }
}
