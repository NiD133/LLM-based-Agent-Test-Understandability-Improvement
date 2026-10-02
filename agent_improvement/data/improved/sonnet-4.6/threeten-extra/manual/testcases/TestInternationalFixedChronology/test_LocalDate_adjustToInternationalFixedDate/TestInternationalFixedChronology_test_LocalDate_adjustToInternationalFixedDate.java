package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_adjustToInternationalFixedDate {

    /**
     * Verifies that using LocalDate.with(InternationalFixedDate) correctly converts an
     * International Fixed date to its ISO equivalent.
     *
     * IFC 2012/07/19 corresponds to ISO 2012-07-06. The adjuster is invoked on
     * LocalDate.MIN, but the result depends only on the IFC date's epoch-day, not
     * on the starting LocalDate value.
     */
    @Test
    public void test_LocalDate_adjustToInternationalFixedDate() {
        InternationalFixedDate ifcDate = InternationalFixedDate.of(2012, 7, 19);
        LocalDate result = LocalDate.MIN.with(ifcDate);
        assertEquals(LocalDate.of(2012, 7, 6), result);
    }
}
