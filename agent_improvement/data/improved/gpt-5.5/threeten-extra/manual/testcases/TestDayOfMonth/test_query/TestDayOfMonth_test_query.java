package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQueries;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_query {

    private static final DayOfMonth TEST_DAY = DayOfMonth.of(12);
    private static final ZoneId TOKYO = ZoneId.of("Asia/Tokyo");

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        assertEquals(LocalDate.now(TOKYO).getDayOfMonth(), DayOfMonth.now(TOKYO).getValue());
    }

    @Test
    public void test_query() {
        assertEquals(IsoChronology.INSTANCE, TEST_DAY.query(TemporalQueries.chronology()));
        assertEquals(null, TEST_DAY.query(TemporalQueries.localDate()));
        assertEquals(null, TEST_DAY.query(TemporalQueries.localTime()));
        assertEquals(null, TEST_DAY.query(TemporalQueries.offset()));
        assertEquals(ChronoUnit.DAYS, TEST_DAY.query(TemporalQueries.precision()));
        assertEquals(null, TEST_DAY.query(TemporalQueries.zone()));
        assertEquals(null, TEST_DAY.query(TemporalQueries.zoneId()));
    }
}
