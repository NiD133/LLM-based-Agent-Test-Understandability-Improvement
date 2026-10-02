package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focusing on {@code getLong} with a null field.
 */
public class TestDayOfMonth_test_getLong_null {

    /** A fixed day-of-month instance shared by the tests. */
    private static final DayOfMonth TEST = DayOfMonth.of(12);

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        int expectedDay = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDay = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDay, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // getLong(TemporalField)
    //-----------------------------------------------------------------------
    @Test
    public void test_getLong_null() {
        // Passing a null field must be rejected with a NullPointerException.
        TemporalField nullField = null;
        assertThrows(NullPointerException.class, () -> TEST.getLong(nullField));
    }
}
