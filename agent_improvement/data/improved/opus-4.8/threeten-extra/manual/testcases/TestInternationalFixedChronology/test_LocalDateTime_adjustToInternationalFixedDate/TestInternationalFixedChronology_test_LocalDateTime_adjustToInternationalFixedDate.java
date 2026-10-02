package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a {@link LocalDateTime} with an {@link InternationalFixedDate}
 * replaces the date part with the ISO-equivalent of the International Fixed date,
 * while leaving the time part untouched.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDateTime_adjustToInternationalFixedDate {

    @Test
    public void test_LocalDateTime_adjustToInternationalFixedDate() {
        // International Fixed date 2012-07-19 corresponds to ISO 2012-07-06.
        InternationalFixedDate fixedDate = InternationalFixedDate.of(2012, 7, 19);

        // Adjusting LocalDateTime.MIN keeps its time-of-day (00:00) and adopts the new date.
        LocalDateTime adjusted = LocalDateTime.MIN.with(fixedDate);

        assertEquals(LocalDateTime.of(2012, 7, 6, 0, 0), adjusted);
    }
}
