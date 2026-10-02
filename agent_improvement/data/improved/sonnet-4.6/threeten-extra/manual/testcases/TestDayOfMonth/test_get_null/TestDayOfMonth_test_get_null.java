package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests that {@link DayOfMonth#get(TemporalField)} throws {@link NullPointerException}
 * when given a null field, and validates basic time-zone-aware day-of-month retrieval.
 */
public class TestDayOfMonth_test_get_null {

    /** A representative {@code DayOfMonth} instance used across tests. */
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    /** Passing null as the temporal field must throw {@link NullPointerException}. */
    @Test
    public void test_get_null() {
        assertThrows(NullPointerException.class, () -> TEST.get((TemporalField) null));
    }
}
