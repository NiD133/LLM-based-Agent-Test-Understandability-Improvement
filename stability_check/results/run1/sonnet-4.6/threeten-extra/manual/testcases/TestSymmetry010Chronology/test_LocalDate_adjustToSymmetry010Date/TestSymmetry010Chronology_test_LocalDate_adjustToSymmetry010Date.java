package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_LocalDate_adjustToSymmetry010Date {

    // Verifies that a Symmetry010Date can act as a TemporalAdjuster to convert any LocalDate
    // to the ISO LocalDate that corresponds to the same point in time.
    @Test
    public void test_LocalDate_adjustToSymmetry010Date() {
        // Symmetry010 date 2012/07/19 maps to ISO date 2012-07-20
        Symmetry010Date sym010Date = Symmetry010Date.of(2012, 7, 19);
        LocalDate isoEquivalent = LocalDate.MIN.with(sym010Date);
        assertEquals(LocalDate.of(2012, 7, 20), isoEquivalent);
    }
}
