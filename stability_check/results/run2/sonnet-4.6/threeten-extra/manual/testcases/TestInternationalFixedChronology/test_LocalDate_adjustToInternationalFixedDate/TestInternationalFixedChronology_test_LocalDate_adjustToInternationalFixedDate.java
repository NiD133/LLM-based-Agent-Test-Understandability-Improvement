package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_LocalDate_adjustToInternationalFixedDate {

    // Verifies that an InternationalFixedDate can adjust a LocalDate via the TemporalAdjuster contract.
    @Test
    public void test_LocalDate_adjustToInternationalFixedDate() {
        InternationalFixedDate ifcDate = InternationalFixedDate.of(2012, 7, 19);
        LocalDate isoDate = LocalDate.MIN.with(ifcDate);
        assertEquals(LocalDate.of(2012, 7, 6), isoDate);
    }
}
