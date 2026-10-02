package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_LocalDateTime_adjustToPaxDate {

    /**
     * Verifies that adjusting a LocalDateTime with a PaxDate converts the date portion
     * from the Pax calendar to its ISO equivalent while preserving the time fields.
     *
     * PaxDate 2012-06-23 corresponds to ISO date 2012-06-04.
     * LocalDateTime.MIN provides midnight (00:00) as the time component.
     */
    @Test
    public void test_LocalDateTime_adjustToPaxDate() {
        PaxDate pax = PaxDate.of(2012, 6, 23);
        LocalDateTime result = LocalDateTime.MIN.with(pax);
        assertEquals(LocalDateTime.of(2012, 6, 4, 0, 0), result);
    }
}
