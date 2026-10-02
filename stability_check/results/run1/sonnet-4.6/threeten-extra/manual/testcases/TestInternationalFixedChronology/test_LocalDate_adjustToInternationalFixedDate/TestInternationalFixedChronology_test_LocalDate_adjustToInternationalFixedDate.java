package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_adjustToInternationalFixedDate {

    // Verifies that adjusting a LocalDate with an InternationalFixedDate yields
    // the correct ISO equivalent date for the given International Fixed date.
    @Test
    public void test_LocalDate_adjustToInternationalFixedDate() {
        InternationalFixedDate ifcDate = InternationalFixedDate.of(2012, 7, 19);
        LocalDate adjustedIsoDate = LocalDate.MIN.with(ifcDate);
        assertEquals(LocalDate.of(2012, 7, 6), adjustedIsoDate);
    }
}
