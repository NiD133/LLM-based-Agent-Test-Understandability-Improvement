package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_get_null {

    /** A representative DayOfYear instance used across tests. */
    private static final DayOfYear TEST = DayOfYear.of(12);

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    @Test
    public void test_get_null() {
        // DayOfYear.get(TemporalField) must reject a null field with NullPointerException
        assertThrows(NullPointerException.class, () -> TEST.get((TemporalField) null));
    }
}
