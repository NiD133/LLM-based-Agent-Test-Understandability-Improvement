package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting an {@link InternationalFixedDate} with a {@link LocalDate}
 * (an ISO calendar date) converts the ISO date into the equivalent
 * International Fixed Calendar date.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        // Start from an arbitrary International Fixed date; its value is irrelevant
        // because adjusting with a LocalDate replaces it entirely.
        InternationalFixedDate startDate = InternationalFixedDate.of(2000, 1, 4);

        // ISO date 2012-07-06 corresponds to International Fixed date 2012/07/19.
        InternationalFixedDate adjusted = startDate.with(LocalDate.of(2012, 7, 6));

        assertEquals(InternationalFixedDate.of(2012, 7, 19), adjusted);
    }
}
