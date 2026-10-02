package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Tests that adjusting a {@link Symmetry010Date} with an ISO {@link LocalDate}
 * yields the equivalent date in the Symmetry010 calendar system.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_adjust_toLocalDate {

    @Test
    public void test_adjust_toLocalDate() {
        // Start from an arbitrary Symmetry010 date.
        Symmetry010Date startDate = Symmetry010Date.of(2000, 1, 4);

        // Adjusting it to ISO 2012-07-06 must move it to the matching Symmetry010 date.
        Symmetry010Date adjustedDate = startDate.with(LocalDate.of(2012, 7, 6));

        // ISO 2012-07-06 corresponds to Symmetry010 2012/07/05.
        assertEquals(Symmetry010Date.of(2012, 7, 5), adjustedDate);
    }
}
