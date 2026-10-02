package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

/**
 * Verifies that a {@link LocalDate} can be adjusted onto the calendar day
 * represented by an {@link InternationalFixedDate}.
 */
@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_adjustToInternationalFixedDate {

    @Test
    public void adjustingLocalDateToInternationalFixedDate_yieldsEquivalentIsoDate() {
        // Given an International Fixed date...
        InternationalFixedDate internationalFixedDate = InternationalFixedDate.of(2012, 7, 19);

        // ...when we adjust an ISO LocalDate to it...
        LocalDate adjusted = LocalDate.MIN.with(internationalFixedDate);

        // ...then we get the ISO date that corresponds to the same day.
        assertEquals(LocalDate.of(2012, 7, 6), adjusted);
    }
}
