package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_LocalDateTime_adjustToPaxDate {

    /**
     * Adjusting a {@link LocalDateTime} with a {@link PaxDate} should move the date part
     * to the ISO date equivalent to that Pax date, while leaving the time part untouched.
     * <p>
     * Pax date 2012-06-23 corresponds to ISO date 2012-06-04, and {@code LocalDateTime.MIN}
     * carries the midnight time (00:00).
     */
    @Test
    public void test_LocalDateTime_adjustToPaxDate() {
        PaxDate paxDate = PaxDate.of(2012, 6, 23);

        LocalDateTime adjusted = LocalDateTime.MIN.with(paxDate);

        LocalDateTime expected = LocalDateTime.of(2012, 6, 4, 0, 0);
        assertEquals(expected, adjusted);
    }
}
