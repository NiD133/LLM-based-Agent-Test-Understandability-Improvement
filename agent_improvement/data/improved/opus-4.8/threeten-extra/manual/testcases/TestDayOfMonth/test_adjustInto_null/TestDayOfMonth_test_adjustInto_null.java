package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.Temporal;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focusing on {@code adjustInto} with a null target.
 */
public class TestDayOfMonth_test_adjustInto_null {

    /** A fixed sample day-of-month used as the test subject. */
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
    // adjustInto(Temporal)
    //-----------------------------------------------------------------------
    @Test
    public void test_adjustInto_null() {
        // Adjusting a null temporal must be rejected with a NullPointerException.
        assertThrows(NullPointerException.class, () -> TEST.adjustInto((Temporal) null));
    }
}
