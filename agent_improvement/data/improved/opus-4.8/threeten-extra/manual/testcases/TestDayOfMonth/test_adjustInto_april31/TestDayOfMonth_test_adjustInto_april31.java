package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth}, focusing on {@code adjustInto} together with the
 * {@code now} factory methods.
 */
public class TestDayOfMonth_test_adjustInto_april31 {

    /**
     * {@code now()} should report the same day-of-month as today's local date.
     * Retried because the clock can roll over to a new day between the two reads.
     */
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    /**
     * {@code now(ZoneId)} should report the same day-of-month as today's local date
     * in that zone. Retried because the clock can roll over between the two reads.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(tokyo).getDayOfMonth();
        assertEquals(expectedDayOfMonth, DayOfMonth.now(tokyo).getValue());
    }

    /**
     * Adjusting April (a 30-day month) to day 31 is impossible, so {@code adjustInto}
     * must throw {@link DateTimeException}.
     */
    @Test
    public void test_adjustInto_april31() {
        LocalDate firstOfApril = LocalDate.of(2007, 4, 1);
        DayOfMonth dayThirtyOne = DayOfMonth.of(31);

        assertThrows(DateTimeException.class, () -> dayThirtyOne.adjustInto(firstOfApril));
    }
}
